package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.route.RouteGroup
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response


open class IrcRouteGroup(ignoreCase: Boolean, prefix: String, vararg routes: IrcRouteGroup) : RouteGroup<Request, Response>(ignoreCase, prefix, *routes)
{
    open val type = NONE
    override fun canTrigger(request: Request): Boolean
    {
        return isEnabled && request.type.and(type) == type && regex.matches(request.message)
    }

    companion object
    {
        /**
         * Default mode. Means that route does not work solely with private messages or channel messages.
         */
        @JvmField
        val NONE: Int = 0
        /**
         * Marks route as ping route. Shouldn't be used outside [Pong]
         */
        @JvmField
        val PING: Int = 4
        /**
         * Marks route as private message route.
         */
        @JvmField
        val PRIVATE: Int = 1
        /**
         * Marks route as channel route.
         */
        @JvmField
        val CHANNEL: Int = 2
    }
}