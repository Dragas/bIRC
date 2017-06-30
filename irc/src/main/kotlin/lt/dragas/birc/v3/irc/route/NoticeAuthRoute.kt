package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * responds to notice auth messages
 */
class NoticeAuthRoute(pattern: String = ".*", callback: (Request) -> Response?) : IrcRoute(Command.NOTICE, Pattern.compile(pattern), callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && request.arguments[0] == "AUTH"
    }
}