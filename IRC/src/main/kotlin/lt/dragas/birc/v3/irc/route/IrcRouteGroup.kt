package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.route.RouteGroup
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response


open class IrcRouteGroup(prefix: String, vararg routes: IrcRoute) : RouteGroup<Request, Response>(prefix, *routes)
{
    open val type = Request.NONE
    override fun canTrigger(request: Request): Boolean
    {
        val canTrigger = isEnabled && request.type.and(type) == type && regex.matches(request.message)
        if (canTrigger)
        {
            request.message = request.message.replaceFirst(regex, "")
        }
        return canTrigger
    }
}