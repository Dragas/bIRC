package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.route.RouteGroup
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response


/**
 * Triggering by type.
 */
open class IrcRouteGroup(open val type: String, prefix: String, vararg routes: IrcRouteGroup) : RouteGroup<Request, Response>(prefix, *routes)
{
    private var built = false
    override fun attemptTrigger(request: Request): Response?
    {
        if (built)
            return super.attemptTrigger(request)
        throw Exception("Route group was not built. Are you sure you called build()?")
    }
    override fun canTrigger(request: Request): Boolean
    {
        val canTrigger = isEnabled && request.command.equals(type, true)
        /*if (canTrigger)
        {
            request.message = request.message.replaceFirst(regex, "")
        }*/
        return canTrigger
    }

    fun build()
    {
        if (type != "")
        {
            routes as Array<out IrcRouteGroup>
            routes.forEach {
                if (it.type != "")
                    throw Exception("Parent group already declares command these routes should respond to")
            }
        }

        built = true
    }
}