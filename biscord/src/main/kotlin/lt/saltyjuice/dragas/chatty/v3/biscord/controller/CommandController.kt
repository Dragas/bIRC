package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import lt.saltyjuice.dragas.chatty.v3.biscord.doIf
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.controller.DiscordController
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse

abstract class CommandController : DiscordController()
{
    protected abstract val command: String
    protected open val arguments: Array<String> = arrayOf()
    protected open var argumentMap: Map<String, Boolean> = mapOf()
    protected open val shouldIgnoreCase: Boolean = false
    @On(EventMessageCreate::class)
    @When("shouldRespond")
    abstract fun onCommand(requst: EventMessageCreate): OPResponse<*>?

    open fun shouldRespond(request: EventMessageCreate): Boolean
    {
        return request.data!!
                .content
                .startsWith(command, shouldIgnoreCase)
                .doIf()
                {
                    request.data!!.content = request.data!!.content.replace(command, "")
                    parseArguments(request.data!!.content)
                }
    }

    open fun parseArguments(message: String)
    {
        val mutableMap = mutableMapOf<String, Boolean>()
        arguments.forEach()
        {
            if (message.contains(it, shouldIgnoreCase))
            {
                mutableMap[it] = true
            }
        }
        argumentMap = mutableMap
    }

    open fun getArgument(argument: String): Boolean
    {
        return argumentMap.getOrElse(argument, { false })
    }
}