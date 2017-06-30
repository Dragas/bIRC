package lt.saltyjuice.dragas.chatty.v3.irc.routing

import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response


/**
 * An IRC implementation of [Route] objects.
 *
 * By default, they only test if provided type matches the request type, as well as if it
 * passes provided test callback and middleware tests.
 */
abstract class IrcRoute : Route<Request, Response>()
{
    protected open var type: String = ""

    override fun canTrigger(request: Request): Boolean
    {
        return request.command == type && super.canTrigger(request)
    }
}