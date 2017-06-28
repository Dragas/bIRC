package lt.dragas.birc.v3.irc.controller

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.route.IrcRouter

/**
 * Handles ping requests from server
 */
class PingController private constructor()
{
    fun onPing(request: Request): Response
    {
        return Response("pong", request.arguments[0])
    }

    companion object
    {
        val instance = PingController()

        @JvmStatic
        fun initialize(router: IrcRouter)
        {

        }
    }
}