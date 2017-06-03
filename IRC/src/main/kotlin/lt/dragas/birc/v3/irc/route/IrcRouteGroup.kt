package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.route.RouteGroup
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response


/**
 * Triggering by type.
 */
open class IrcRouteGroup(prefix: String, vararg routes: IrcRoute) : RouteGroup<Request, Response>(prefix, *routes)
{
    open val type = ""
    override fun canTrigger(request: Request): Boolean
    {
        val canTrigger = isEnabled && request.command.equals(type, true)
        /*if (canTrigger)
        {
            request.message = request.message.replaceFirst(regex, "")
        }*/
        return canTrigger
    }
}