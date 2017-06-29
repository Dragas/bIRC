package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * A command route.
 *
 * Handles your usual commands like 422, 114. The difference here is that pattern argument is omitted,
 * since nearly always you won't need to act differently if there's no particular argument in response.
 *
 * Sometimes you need to, but usually you don't, hence why [pattern] defaults to ".*"
 *
 * @param type command this route responds to, usually defined in [IrcRouter]
 * @param callback a callback to invoke when this route triggers.
 * @param pattern a pattern to test against when this route is supposed to trigger
 */
class CommandRoute(type: Command, callback: (Request) -> Response?, pattern: String = ".*") : IrcRoute(type, Pattern.compile(pattern), callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return super.canTrigger(request) && pattern.matcher(request.arguments.joinToString(" ")).matches()
    }
}