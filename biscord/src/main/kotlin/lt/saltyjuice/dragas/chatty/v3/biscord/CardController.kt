package lt.saltyjuice.dragas.chatty.v3.biscord

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
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

class CardController : DiscordController(), Callback<ArrayList<Card>>
{
    private var arguments = Array(1, { "" })
    private var shouldBeGold = false
    private var shouldBeVerbose = false
    private var shouldBeMany = false

    init
    {
        BiscordUtility.API.getCards().enqueue(this)
    }

    override fun onResponse(call: Call<ArrayList<Card>>, response: Response<ArrayList<Card>>)
    {
        if (response.isSuccessful)
            cardss = response.body()!!
        else
            throw IllegalStateException("Failed to get cards from API")
    }

    override fun onFailure(call: Call<ArrayList<Card>>, up: Throwable)
    {
        throw up
    }


    @On(EventMessageCreate::class)
    @When("isCardRequest")
    @Before(MentionsMe::class)
    fun onCardRequest(request: EventMessageCreate): OPResponse<*>?
    {
        val content = request.data!!
        beforeRequest(request)
        var messageBuilder = MessageBuilder()
        if (shouldBeGold)
        {
            MessageBuilder().append("Due to changes in how cards are obtained, GOLDEN versions are unavailable. Just omit the -g/--gold modifier").send(content.channelId)
            return null
        }
        getCards().parallelStream().use {
            if (shouldBeMany)
            {
                it.filter(this::cardFilter)
                if (shouldBeVerbose)
                {
                    it.forEach()
                    {
                        messageBuilder
                                .mention(content.author)
                                .embed(it.toEmbed(shouldBeGold))
                                .send(content.channelId)
                        messageBuilder = MessageBuilder()
                    }
                }
                else
                {

                    it.forEach()
                    {
                        try
                        {
                            val image = if (shouldBeGold) it.imgGold else it.img
                            messageBuilder.appendLine(image)
                        }
                        catch (err: MessageBuilderException)
                        {
                            messageBuilder.send(request.data!!.channelId)
                            messageBuilder = MessageBuilder()
                        }
                    }
                    messageBuilder.send(content.channelId)
                }
            }
            else
            {
                it.findFirst()
                        .filter(this::cardFilter)
                        .flatMap<Card>()
                        {
                            val image = if (shouldBeGold) it.imgGold else it.img
                            messageBuilder
                                    .mention(content.author)
                                    .appendLine(image)
                                    .send(content.channelId)
                            Optional.of(it)
                        }
                        .orElseGet()
                        {
                            messageBuilder
                                    .mention(content.author)
                                    .appendLine(": can't find ${arguments[0]}.")
                                    .send(content.channelId)
                            Card()
                        }
            }

        }

        return null
    }

    fun cardFilter(card: Card): Boolean
    {
        return card.dbfId.toString() == arguments[0]
                || card.name.contains(arguments[0], true)
                || card.cardId.toLowerCase() == arguments[0]
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
        private var cardss = ArrayList<Card>()

        @JvmStatic
        fun getCards(): List<Card>
        {
            return cardss
        }

        @JvmStatic
        fun getCardById(dbfId: Int, channel: Channel<Card>) = launch(CommonPool)
        {
            val cards = getCards()
            val card = cards.parallelStream().findFirst().filter { it.dbfId == dbfId }.get()
            channel.send(card)
        }
    }
}