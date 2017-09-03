package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Unconfined
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.produce
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.biscord.doIf
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Type
import lt.saltyjuice.dragas.chatty.v3.biscord.middleware.MentionsMe
import lt.saltyjuice.dragas.chatty.v3.biscord.utility.BiscordUtility
import lt.saltyjuice.dragas.chatty.v3.core.route.Before
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.discord.controller.DiscordController
import lt.saltyjuice.dragas.chatty.v3.discord.exception.MessageBuilderException
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.stream.Stream
import kotlin.streams.toList

class CardController : DiscordController()
{
    private var arguments = Array(1, { "" })
    private var shouldBeGold = false
    private var shouldBeVerbose = false
    private var shouldBeMany = false
    private var shouldIncludeCreated = false
    private var messageBuilder = MessageBuilder()
        @Synchronized
        get()
        {
            return field
        }
        @Synchronized
        set(value)
        {
            field = value
        }
    private lateinit var content: Message
    private val exceptionMap: HashMap<String, String> = hashMapOf(
            Pair("Pot of Greed", "Arcane Intellect"),
            Pair("Black Lotus", "Innervate"),
            Pair("Dr. Balance", "Dr. Boom"),
            Pair("Dr Balance", "Dr. Boom"),
            Pair("Shit", "\"(You)\"")
    )

    fun isCardRequest(request: EventMessageCreate): Boolean
    {
        val content = request.data!!
        return containsArgument(content, Param.CARD.values[0])
    }

    private fun containsArgument(request: Message, param: String): Boolean
    {
        return request.content.toLowerCase().startsWith(param).doIf {
            request.content = request.content.replaceFirst(param, "")
            request.content = request.content.replaceFirst(" ", "")
        }
    }

    private fun beforeRequest(request: EventMessageCreate)
    {
        arguments = parseArguments(request.data!!.content)
        beforeRequest()
    }

    private fun beforeRequest()
    {
        val exception = exceptionMap.keys.find { it.toLowerCase() == arguments[0].toLowerCase() }
        if (exception != null)
            arguments[0] = exceptionMap[exception]!!
        shouldBeGold = containsArgument(Param.GOLD)
        shouldBeVerbose = containsArgument(Param.VERBOSE)
        shouldBeMany = containsArgument(Param.MANY)
        shouldIncludeCreated = containsArgument(Param.CREATES)
    }

    private fun containsArgument(param: Param): Boolean
    {
        return containsArgument(param.values)
    }

    private fun containsArgument(param: Array<out String>): Boolean
    {
        return param.find { value -> arguments.contains(value) } != null
    }

    private fun parseArguments(request: String): Array<String>
    {
        val args = request.split(Regex("\\s+-{1,2}"))

        return args.toTypedArray()
    }

    fun pushArguments(array: Array<String>)
    {
        arguments = array
        beforeRequest()
    }


    @On(EventMessageCreate::class)
    @When("isCardRequest")
    @Before(MentionsMe::class)
    fun onCardRequest(request: EventMessageCreate): OPResponse<*>?
    {
        content = request.data!!
        beforeRequest(request)
        messageBuilder = MessageBuilder()
        if (shouldBeGold)
        {
            MessageBuilder().append("Due to changes in how cards are obtained, GOLDEN versions are unavailable. Just omit the -g/--gold modifier").send(content.channelId)
            return null
        }

        buildMessage(filterCards(this::onNoneFound))
        return null
    }

    private fun cardFilter(card: Card): Boolean
    {
        return card.dbfId.toString() == arguments[0]
                || card.cardId.toLowerCase() == arguments[0]
    }

    private fun cardFilterSingle(card: Card): Boolean
    {
        return cardFilter(card) || card.name.toLowerCase() == arguments[0].toLowerCase()
    }

    private fun cardFilterMany(card: Card): Boolean
    {
        return cardFilter(card) || card.name.contains(arguments[0], true)
    }

    fun getFilteredForMany(it: Stream<Card>): Stream<Card>
    {
        return it.filter(this::cardFilterMany)
    }

    fun getFilteredForSingle(it: Stream<Card>): Stream<Card>
    {
        return it.filter(this::cardFilterSingle)
    }

    fun filterForMany(it: Stream<Card>): List<Card>
    {
        return getFilteredForMany(it).toList()
    }

    fun filterForSingle(it: Stream<Card>): Card
    {
        return getFilteredForSingle(it)
                .findFirst()
                .orElseGet()
                {
                    Card()
                }
    }

    private fun buildMessage(data: List<Card>)
    {
        val count = data.size
        if (count > 0)
        {
            if (shouldBeVerbose)
            {
                data.forEach(this::buildVerbose)
            }
            else
            {
                data.forEach(this::buildSimple)
            }
        }
        else
        {
            messageBuilder.appendLine("None of the collectible cards matched ${arguments[0]}")
        }
        messageBuilder.send(content.channelId)
    }


    @JvmOverloads
    fun filterCards(onNoneFound: ((cardList: MutableList<Card>) -> Unit)? = null): List<Card>
    {
        return getCollectable().parallelStream().use {
            val cardList = ArrayList<Card>()
            if (shouldBeMany)
            {
                cardList.addAll(filterForMany(it))
            }
            else
            {
                val card = filterForSingle(it)
                if (card.dbfId == -1)
                {
                    onNoneFound?.invoke(cardList)
                }
                else
                {
                    cardList.add(card)
                }
            }
            cardList
        }
    }

    private fun buildVerbose(card: Card)
    {
        messageBuilder
                .mention(content.author)
                .embed(card.toEmbed(shouldBeGold))
                .send(content.channelId)
        messageBuilder = MessageBuilder()
    }

    private fun buildSimple(card: Card)
    {
        val image = if (shouldBeGold) card.imgGold else card.img
        try
        {
            if (card.collectible)
            {
                messageBuilder.append(image)
                messageBuilder.appendLine(" ")
            }
            else
            {
                buildSimpleCodeSnippet(card)
            }
            if (card.entourages.isNotEmpty())
            {
                if (!shouldIncludeCreated)
                {
                    messageBuilder
                            .append(" creates ${card.entourages.count()} cards. Use \"")
                            .mention(ConnectionController.getCurrentUser())
                            .append(" card ${card.name} --creates\" to include them.")
                }
                else
                {
                    messageBuilder.appendLine("API does not include images for generated cards.")
                    messageBuilder.send(content.channelId)
                    messageBuilder = MessageBuilder()
                    card.entourages.forEach(this::buildSimpleCodeSnippet)
                }
            }
        }
        catch (err: MessageBuilderException)
        {
            messageBuilder.send(content.channelId)
            messageBuilder = MessageBuilder()
            messageBuilder.appendLine(image)
        }
    }

    private fun buildSimpleCodeSnippet(card: Card)
    {
        try
        {
            messageBuilder.beginCodeSnippet("markdown")
                    .appendLine("[${card.name}][${card.dbfId}]{${card.cardId}]")
                    .appendLine("[${card.cost} mana]")
            when (card.type)
            {
                Type.Minion -> messageBuilder.appendLine("[${card.attack}/${card.health}]")
            }
            messageBuilder.appendLine(card.text.replace("\r", "").replace("\n", ""))
            messageBuilder.endCodeSnippet()
            messageBuilder.send(content.channelId)

        }
        catch (err: MessageBuilderException)
        {
            err.printStackTrace(System.err)
        }
        messageBuilder = MessageBuilder()
    }

    fun onNoneFound(cards: MutableList<Card>)
    {
        messageBuilder
                .mention(content.author)
                .appendLine(": can't find ${arguments[0]}. Falling back to --many.")
                .appendLine("Note: This search does not include not collectible cards (tokens, hero powers) anymore.")
                .send(content.channelId)
        messageBuilder = MessageBuilder()
        cards.addAll(getCollectable().parallelStream().use(this::filterForMany))
        if (cards.isEmpty())
        {
            messageBuilder
                    .mention(content.author)
                    .appendLine(": can't find ${arguments[0]} in collectible list. Including not collectibles.")
                    .send(content.channelId)
            messageBuilder = MessageBuilder()
            cards.addAll(getCards().parallelStream().use(this::filterForMany))
        }
    }

    private enum class Param(vararg val values: String)
    {
        CARD("card"),
        MANY("many", "m"),
        VERBOSE("verbose", "v"),
        GOLD("gold", "g"),
        CREATES("creates", "c");
    }

    companion object : Callback<Set<Card>>
    {
        @JvmStatic
        private var cardss = setOf<Card>()

        @JvmStatic
        fun initialize()
        {
            if (cardss.isNotEmpty())
                return
            BiscordUtility.API.getCards().apply()
            {
                try
                {
                    val response = this.execute()
                    onResponse(this, response)
                }
                catch (err: Throwable)
                {
                    onFailure(this, err)
                }
            }
        }

        @JvmStatic
        private var collectableCards = setOf<Card>()

        @JvmStatic
        fun getCollectable(): Set<Card>
        {
            return collectableCards
        }

        @JvmStatic
        fun getCards(): Set<Card>
        {
            return cardss
        }

        @JvmStatic
        suspend fun getCardById(dbfId: Int, channel: SendChannel<Card>)
        {
            val cards = getCards()
            cards.parallelStream()
                    .filter { it.dbfId == dbfId }
                    .findFirst()
                    .ifPresent { launch(CommonPool) { channel.send(it) } }
        }

        @JvmStatic
        suspend fun getCardById(dbfId: String, channel: SendChannel<Card>)
        {
            val cards = getCards()
            cards.parallelStream()
                    .filter { it.cardId == dbfId }
                    .findFirst()
                    .ifPresent { launch(CommonPool) { channel.send(it) } }

        }

        @JvmStatic
        override fun onResponse(call: Call<Set<Card>>, response: Response<Set<Card>>)
        {
            if (response.isSuccessful)
            {
                cardss = response.body()!!
                collectableCards = cardss
                        .parallelStream()
                        .filter { it.collectible }
                        .toList()
                        .toSortedSet()
                collectableCards.forEach()
                { entoraging ->
                    val channel = produce<Card>(Unconfined, Channel.UNLIMITED)
                    {
                        for (id in entoraging.entourage)
                        {
                            getCardById(id, this.channel)
                        }
                    }
                    runBlocking()
                    {
                        for (card in channel)
                            entoraging.entourages.add(card)
                    }

                }
            }
            else
                throw IllegalStateException("Failed to get cards from API")
        }

        @JvmStatic
        override fun onFailure(call: Call<Set<Card>>, up: Throwable)
        {
            throw up
        }
    }
}