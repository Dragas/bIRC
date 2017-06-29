package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.routing.Router
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

/**
 * A router implementation for IRC Servers
 */
open class IrcRouter : Router<Request, Response>()
{
    /**
     * Equivalent to calling `when`(Command.CHANNEL_MESSAGE, pattern, callback)
     */
    override fun `when`(pattern: String, callback: (Request) -> Response?): IrcRouter
    {
        return `when`(Command.CHANNEL_MESSAGE, pattern, callback)
    }

    /**
     * Equivalent to calling `when`(type, ".*", callback)`
     */
    open fun `when`(type: Command, callback: (Request) -> Response?): IrcRouter
    {
        return `when`(type, ".*", callback)
    }

    /**
     * Builds a route with provided [type], [pattern] and callback. Do note that type also changes
     * the route object that's returned, since different route types test everything differently
     */
    open fun `when`(type: Command, pattern: String, callback: (Request) -> Response?): IrcRouter
    {
        return `when`(buildRoute(type, pattern, callback)) as IrcRouter
    }

    override fun buildRoute(pattern: String, callback: (Request) -> Response?): IrcRoute?
    {
        return buildRoute(Command.CHANNEL_MESSAGE, pattern, callback)
    }

    open fun buildRoute(type: Command, pattern: String, callback: (Request) -> Response?): IrcRoute?
    {
        when (type)
        {
            Command.PRIVATE_MESSAGE -> return PrivateMessageRoute(pattern, callback)
            Command.CHANNEL_MESSAGE -> return ChannelMessageRoute(pattern, callback)
            else -> return CommandRoute(type, callback, pattern)
        }
    }
}