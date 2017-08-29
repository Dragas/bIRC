package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ProducerJob
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.PlayerClass
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.controller.DiscordController
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import java.util.*
import kotlin.collections.HashMap

open class DeckController : DiscordController()
{
    private var byteArray: ByteArray? = null
    private var hash: String = ""
    private var format: Format = Format.Invalid // 0 implies invalid
    private var version: Int = 0
    private var numberOfHeroes: Int = 0 // should be 1
    private var heroClass: PlayerClass = PlayerClass.Neutral // should be something else
    private var deck: HashMap<Card, Int> = HashMap()
    private var heroId: Int = -1
    private var offset = 0
    private var decoder: ProducerJob<Int>? = null

    fun obtainFormat(): Format
    {
        return format
    }

    fun obtainVersion(): Int
    {
        return version
    }

    fun obtainNumberOfHeroes(): Int
    {
        return numberOfHeroes
    }

    fun obtainHeroClass(): PlayerClass
    {
        return heroClass
    }

    fun obtainDeck(): HashMap<Card, Int>
    {
        return deck
    }

    @Throws(IllegalArgumentException::class)
    private fun getByteArray(hash: String): ByteArray
    {
        byteArray = Base64.getDecoder().decode(hash)

        return byteArray!!
    }

    fun canDecode(hash: String): Boolean
    {
        offset = 0
        try
        {
            getByteArray(hash)
            val initialByte = readInt() == 0
            this.hash = hash
            if (initialByte)
                decoder = initializeDecoder()
            else
                offset = 0
            return initialByte
        }
        catch (err: Throwable)
        {
            return false
        }
    }

    fun decodeAsDeck() = runBlocking<Unit>
    {
        val decoder = decoder!!
        deck = HashMap<Card, Int>()
        version = decoder.receive()
        format = Format.values().getOrElse(decoder.receive(), { Format.Invalid })
        numberOfHeroes = decoder.receive()
        heroId = decoder.receive()
        heroClass = PlayerClass.getById(heroId)
        if (version != 1 || format == Format.Invalid || numberOfHeroes != 1 || heroClass == PlayerClass.Neutral)
        {
            offset = 0
            decoder.cancel()
            this@DeckController.decoder = null
            //throw DeckParsingException("invalid deck")
        }
        val deck = Channel<Card>(Channel.UNLIMITED)
        val jobs = ArrayList<Job>()
        repeat(2)
        { multiplier ->
            val count = decoder.receive()
            repeat(count)
            {
                val id = decoder.receive()
                //val count = decoder.receive()
                /*if (multiplier == 2)
                {
                    repetitions = count
                }*/
                repeat(multiplier + 1)
                {
                    addCardToDeck(id, jobs, deck)
                }
            }
        }
        val multiples = decoder.receiveOrNull()
        if (multiples != null)
        {
            repeat(multiples)
            {
                val id = decoder.receive()
                val count = decoder.receive()
                repeat(count)
                {
                    addCardToDeck(id, jobs, deck)
                }
            }
        }
        for (card in deck)
        {
            //val card = deck.receive()
            var count = this@DeckController.deck.getOrDefault(card, 0)
            count++
            this@DeckController.deck[card] = count
        }
    }

    fun decodeTest(request: EventMessageCreate): Boolean
    {
        val data = request.data!!.content.split(" ")
        return data.find(this::canDecode) != null
    }

    private fun addCardToDeck(id: Int, jobs: ArrayList<Job>, deck: Channel<Card>)
    {
        launch(CommonPool)
        {
            CardController.getCardById(id, deck)
        }.apply()
        {
            jobs.add(this)
            this.invokeOnCompletion()
            {
                jobs.remove(this)
                if (jobs.isEmpty())
                    deck.close()
            }
        }
    }

    fun readInt(): Int
    {
        var length = 0
        var result = 0
        val bytes = byteArray!!.drop(offset)
        do
        {
            val read = bytes[length].toInt()
            val value = read.and(0x7f)
            result = result.or(value.shl(length * 7))
            length++
        }
        while (read.and(0x80) == 0x80)
        offset += length
        return result
    }

    fun initializeDecoder() = produce<Int>(CommonPool, Channel.UNLIMITED)
    {
        while (offset < byteArray!!.size)
            send(readInt())
    }

    @On(EventMessageCreate::class)
    @When("decodeTest")
    fun onDecodeRequest(eventMessageCreate: EventMessageCreate): OPResponse<*>?
    {
        //decoder = initializeDecoder()
        decodeAsDeck()
        val messageBuilder = MessageBuilder()
        messageBuilder.appendLine("# Class: ${this.heroClass.name}")
        messageBuilder.appendLine("# Format: ${this.format.name}")
        deck.toList().sortedWith(Comparator { it1, it2 -> it1.first.cost - it2.first.cost }).forEach()
        { (card, count) ->
            messageBuilder.appendLine("# ${count}x (${card.cost}) ${card.name}")
        }
        messageBuilder.send(eventMessageCreate.data!!.channelId, messageCallback)
        return null
    }

    enum class Format(val value: Int)
    {
        Invalid(0),
        Wild(1),
        Standard(2)
    }


}