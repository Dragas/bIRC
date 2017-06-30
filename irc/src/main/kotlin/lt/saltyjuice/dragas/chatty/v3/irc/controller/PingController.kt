package lt.saltyjuice.dragas.chatty.v3.irc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter

/**
 * Handles ping requests from server
 */
class PingController private constructor()
{
    fun onPing(request: Request): Response
    {
        var destination = request.arguments[0]
        if (request.arguments.size > 1)
            destination = request.arguments[1]
        return Response(Command.PONG.value, destination)
    }

    companion object
    {
        val instance = PingController()

        @JvmStatic
        fun initialize(router: IrcRouter)
        {
            router.`when`(Command.PING, instance::onPing)
        }
    }
}