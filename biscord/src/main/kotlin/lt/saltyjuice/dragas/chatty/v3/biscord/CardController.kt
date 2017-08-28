package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
import lt.saltyjuice.dragas.chatty.v3.core.route.Before
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.controller.DiscordController
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardController : DiscordController()
{
    private var arguments = Array(1, { "" })
    private var shouldBeGold = false
    private var shouldBeVerbose = false
    private var shouldBeMany = false


    @On(EventMessageCreate::class)
    @When("isCardRequest")
    @Before(MentionsMe::class)
    fun onCardRequest(request: EventMessageCreate): OPResponse<*>?
    {
        val content = request.data!!
        beforeRequest(request)
        val cardRequest = CardRequest(content, messageCallback)
        cardRequest.shouldBeGold = shouldBeGold
        cardRequest.shouldBeVerbose = shouldBeVerbose
        cardRequest.shouldBeMany = shouldBeMany
        val call = if (shouldBeMany) BiscordUtility.API.getCards(arguments[0]) else BiscordUtility.API.getSingleCard(arguments[0])
        call.enqueue(cardRequest)
        return null
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
        val content = request.data!!
        BiscordUtility.keepTyping(content.channelId)
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

    private class CardRequest(val content: Message, val messageCallback: Callback<Message>) : Callback<ArrayList<Card>>
    {
        var shouldBeVerbose: Boolean = false
        var shouldBeGold: Boolean = false
        var shouldBeMany: Boolean = false
        var retried: Boolean = false
        override fun onResponse(call: Call<ArrayList<Card>>, response: Response<ArrayList<Card>>)
        {
            val content = content
            var embedMessage = MessageBuilder()
            if (response.isSuccessful)
            {
                val data = response.body()!!
                if (shouldBeVerbose)
                {
                    data.map()
                    {
                        it.toEmbed(shouldBeGold)
                    }.forEachIndexed()
                    { index, value ->
                        embedMessage.append("Card ${index + 1}/${data.size}")
                        embedMessage.embed(value)
                        embedMessage.send(content.channelId, messageCallback)
                        embedMessage = MessageBuilder()
                    }
                }
                else
                {
                    data.forEach {
                        val image = if (shouldBeGold) it.imgGold else it.img
                        embedMessage.appendLine(image)
                    }
                    embedMessage.send(content.channelId, messageCallback)
                }
            }
            else
            {
                val argument = call.request().url().pathSegments().last()
                if (!(shouldBeMany || retried))
                {
                    retried = true
                    embedMessage.mention(content.author).append("Falling back to --many.").send(content.channelId, messageCallback)
                    //Utility.discordAPI.createMessage(content.channelId, "<@${content.author.id}> Falling back to --many")
                    BiscordUtility.API.getCards(argument).enqueue(this)
                }
                else
                {
                    Utility.discordAPI.createMessage(content.channelId, "<@${content.author.id}> Unable to find $argument. Error code: ${response.code()}").enqueue(messageCallback)
                }
            }
            BiscordUtility.cancelTyping(content.channelId)
        }

        override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable)
        {
            t.printStackTrace(System.err)
        }
    }
}