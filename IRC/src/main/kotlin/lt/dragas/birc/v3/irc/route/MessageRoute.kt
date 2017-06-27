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
 */
open class MessageRoute(pattern: String, callback: (Request) -> Response) : IrcRoute(IrcRouter.MESSAGE, Pattern.compile(pattern), callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && pattern.matcher(request.arguments[1]).matches()
    }
}