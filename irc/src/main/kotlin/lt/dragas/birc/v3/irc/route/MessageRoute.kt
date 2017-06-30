package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * IRC route implementation that's used for private messages.
 *
 * Channel messages and Private messages use same command PRIVMSG to send the messages between targets.
 * This particular implementation does not check for that and is just a general implementation.
 * Should you need something specific use [ChannelMessageRoute] or [PrivateMessageRoute] instead.
 * @param pattern a pattern against which the request is tested against
 * @param callback a callback which is invoked when test has passed successfully
 */
open class MessageRoute(pattern: String, callback: (Request) -> Response?) : IrcRoute(Command.PRIVMSG, Pattern.compile(pattern), callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && pattern.matcher(request.arguments[1]).matches()
    }
}