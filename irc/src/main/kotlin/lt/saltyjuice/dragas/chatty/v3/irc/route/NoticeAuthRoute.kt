package lt.saltyjuice.dragas.chatty.v3.irc.route

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * responds to notice auth messages
 */
@Deprecated("Should be handled by implementations instead")
open class NoticeAuthRoute(pattern: String = ".*", callback: (Request) -> Response?) : IrcRoute(Command.NOTICE, Pattern.compile(pattern), callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && request.arguments[0] == "AUTH"
    }
}