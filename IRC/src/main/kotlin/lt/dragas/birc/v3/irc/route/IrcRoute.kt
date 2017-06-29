package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.routing.Route
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * An IRC implementation of [Route] objects.
 *
 * By default, they only test if provided type matches the request type,
 * implementations should test the request arguments according to its type, for example
 * private message routes should test whether or not private message requests are meant for channels.
 */
abstract class IrcRoute(open protected val type: String, pattern: Pattern, callback: (Request) -> Response?) : Route<Request, Response>(pattern, callback)
{
    override fun canTrigger(request: Request): Boolean
    {
        return request.command == type
    }
}