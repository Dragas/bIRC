package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

/**
 * An implementation of [MessageRoute] for private messages.
 *
 * All it does is check whether or not first argument starts with a #, which means that the message
 * is meant for the channel.
 */
class PrivateMessageRoute(pattern: String, callback: (Request) -> Response) : MessageRoute(pattern, callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && !request.arguments[0].startsWith("#")
    }
}