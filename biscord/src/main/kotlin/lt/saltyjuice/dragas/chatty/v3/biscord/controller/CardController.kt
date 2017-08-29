package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import kotlinx.coroutines.experimental.channels.Channel
import lt.saltyjuice.dragas.chatty.v3.biscord.doIf
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
import lt.saltyjuice.dragas.chatty.v3.biscord.middleware.MentionsMe
import lt.saltyjuice.dragas.chatty.v3.biscord.utility.BiscordUtility
import lt.saltyjuice.dragas.chatty.v3.core.route.Before
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
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
import java.util.concurrent.ConcurrentSkipListSet
import java.util.stream.Stream
import kotlin.streams.toList

class CardController : DiscordController(), Callback<ArrayList<Card>>
{
    private var arguments = Array(1, { "" })
    private var shouldBeGold = false
    private var shouldBeVerbose = false
    private var shouldBeMany = false
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
    init
    {
        if (cardss.isEmpty())
        {
            BiscordUtility.API.getCards().apply()
            {
                try
                {
                    val response = this.execute()
                    onResponse(this, response)
                }
                catch (err: Throwable)
                {
                    onFailure(this@apply, err)
                }
            }
        }
    }

    override fun onResponse(call: Call<ArrayList<Card>>, response: Response<ArrayList<Card>>)
    {
        if (response.isSuccessful)
            cardss = ConcurrentSkipListSet(response.body()!!.toSet())
        else
            throw IllegalStateException("Failed to get cards from API")
    }

    override fun onFailure(call: Call<ArrayList<Card>>, up: Throwable)
    {
        throw up
    }

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
        shouldBeGold = containsArgument(Param.GOLD.values)
        shouldBeVerbose = containsArgument(Param.VERBOSE.values)
        shouldBeMany = containsArgument(Param.MANY.values)
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
    fun filterCards(onNoneFound: (() -> Unit)? = null): List<Card>
    {
        return getCards().parallelStream().use {
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
                    onNoneFound?.invoke()
                    cardList.addAll(getCards().parallelStream().use(this::filterForMany))
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
        try
        {
            val image = if (shouldBeGold) card.imgGold else card.img
            messageBuilder.appendLine(image)
        }
        catch (err: MessageBuilderException)
        {
            messageBuilder.send(content.channelId)
            messageBuilder = MessageBuilder()
        }
    }

    fun onNoneFound()
    {
        messageBuilder
                .mention(content.author)
                .appendLine(": can't find ${arguments[0]}. Falling back to --many.")
                .appendLine("Note: This search does not include not collectible cards (tokens, hero powers) anymore.")
    }

    private enum class Param(vararg val values: String)
    {
        CARD("card"),
        MANY("many", "m"),
        VERBOSE("verbose", "v"),
        GOLD("gold", "g");
    }

    companion object
    {
        @JvmStatic
        private var cardss = ConcurrentSkipListSet<Card>()

        @JvmStatic
        fun getCards(): Set<Card>
        {
            return cardss
        }

        @JvmStatic
        suspend fun getCardById(dbfId: Int, channel: Channel<Card>)
        {
            val cards = getCards()
            val card = cards.parallelStream().filter { it.dbfId == dbfId }.findFirst().get()
            channel.send(card)
        }
    }
}