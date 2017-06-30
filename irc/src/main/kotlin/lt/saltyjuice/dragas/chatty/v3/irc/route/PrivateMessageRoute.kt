package lt.saltyjuice.dragas.chatty.v3.irc.route

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response

/**
 * An implementation of [MessageRoute] for private messages.
 *
 * All it does is check whether or not first argument starts with a #, which means that the message
 * is meant for the channel.
 * @param pattern a pattern that the request is is tested against
 * @param callback a callback that's invoked when request passes the test
 */
@Deprecated("Use middleware instead")
open class PrivateMessageRoute(pattern: String, callback: (Request) -> Response?) : MessageRoute(pattern, callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && !request.arguments[0].startsWith("#")
    }
}