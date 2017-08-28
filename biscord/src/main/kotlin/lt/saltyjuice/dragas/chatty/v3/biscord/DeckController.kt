package lt.saltyjuice.dragas.chatty.v3.biscord

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.ProducerJob
import kotlinx.coroutines.experimental.channels.produce
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
    var format: Format = Format.Invalid // 0 implies invalid
    var version: Int = 0
    var numberOfHeroes: Int = 0 // should be 1
    var heroClass: PlayerClass = PlayerClass.Neutral // should be something else
    var deck: HashMap<Card, Int> = HashMap()
    var heroId: Int = -1

    var offset = 0

    @Throws(IllegalArgumentException::class)
    fun getByteArray(hash: String): ByteArray
    {
        byteArray = Base64.getDecoder().decode(hash)

        return byteArray!!
    }

    fun canDecode(hash: String): Boolean
    {
        try
        {
            getByteArray(hash)
            val initialByte = readInt()
            this.hash = hash
            return initialByte == 0
        }
        catch (err: IllegalArgumentException)
        {
            return false
        }
    }

    fun decodeAsDeck() = runBlocking<Unit>
    {
        val decoder = decoder!!
        version = decoder.receive()
        format = Format.values()[decoder.receive()]
        numberOfHeroes = decoder.receive()
        heroId = decoder.receive()
        heroClass = PlayerClass.getById(heroId)
        val deck = Channel<Card>(Channel.UNLIMITED)
        repeat(3)
        { multiplier ->
            val count = decoder.receive()
            repeat(count)
            {
                val id = decoder.receive()
                val count = decoder.receive()
                var repetitions = multiplier + 1
                if (multiplier == 2)
                {
                    repetitions = count
                }
                repeat(repetitions)
                {
                    CardController.getCardById(id, deck)
                    if (multiplier != 2)
                        CardController.getCardById(count, deck)
                }
            }
        }
        for (card in deck)
        {
            var count = this@DeckController.deck.getOrDefault(card, 0)
            count++
            this@DeckController.deck[card] = count
        }
        deck.close()
    }

    fun decodeTest(request: EventMessageCreate): Boolean
    {
        val data = request.data!!.content.split(" ")
        return data.find(this::canDecode) != null
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

    fun decode() = produce<Int>(CommonPool, Channel.UNLIMITED)
    {
        while (offset < byteArray!!.size)
            send(readInt())
        close()
    }

    private var decoder: ProducerJob<Int>? = null

    @On(EventMessageCreate::class)
    @When("decodeTest")
    fun onDecodeRequest(eventMessageCreate: EventMessageCreate): OPResponse<*>?
    {
        decoder = decode()
        decodeAsDeck()
        decoder?.cancel()
        decoder = null
        val messageBuilder = MessageBuilder()
        messageBuilder.appendLine("# Class: ${this.heroClass.name}")
        messageBuilder.appendLine("# Format: ${this.format.name}")
        deck.forEach()
        {
            messageBuilder.appendLine("# ${it.value}x (${it.key.cost}) ${it.key}")
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