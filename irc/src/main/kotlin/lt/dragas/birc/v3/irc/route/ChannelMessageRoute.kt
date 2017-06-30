package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

/**
 * An implementation for channel messages.
 *
 * All it does is check whether or not first argument starts with a #, which means that the message
 * is meant for the channel.
 * @param pattern a pattern which the private message is tested against
 * @param callback callback to invoke when testing against pattern succeeds
 */
class ChannelMessageRoute(pattern: String, callback: (Request) -> Response?) : MessageRoute(pattern, callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && request.arguments[0].startsWith("#")
    }
}