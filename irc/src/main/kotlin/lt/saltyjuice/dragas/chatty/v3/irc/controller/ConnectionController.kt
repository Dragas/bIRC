package lt.saltyjuice.dragas.chatty.v3.irc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.IrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter

/**
 * Handles connecting to channels once RPL welcome message is hit.
 */
class ConnectionController
{
    fun onConnect(request: Request): Response
    {
        val response = Response("")
        settings.channels.forEach {
            response.otherResponses.add(Response(Command.JOIN, it))
        }
        return response
    }

    companion object
    {
        @JvmStatic
        val instance = ConnectionController()

        @JvmStatic
        var settings: IrcSettings = IrcSettings()

        @JvmStatic
        fun initialize(router: IrcRouter, settings: IrcSettings)
        {
            Companion.settings = settings
            router.add(router.builder().let {
                it.callback(instance::onConnect)
                it.type(Command.RPL_WELCOME)
                it.build()
            })
        }
    }
}