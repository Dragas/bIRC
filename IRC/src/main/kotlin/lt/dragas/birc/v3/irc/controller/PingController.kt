package lt.dragas.birc.v3.irc.controller

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

/**
 * Handles ping requests from server
 */
class PingController
{
    fun onPing(request: Request): Response
    {
        return Response("pong", request.arguments[0])
    }
}