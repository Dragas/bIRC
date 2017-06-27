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
     * Equivalent to calling [when(CHANNEL_MESSAGE, String, (Request)->Response)]
     */
    override fun `when`(pattern: String, callback: (Request) -> Response): IrcRouter
    {
        return `when`(CHANNEL_MESSAGE, pattern, callback)
    }

    /**
     * Builds a route with provided [type], [pattern] and callback. Do note that type also changes
     * the route object that's returned, since different route types test everything differently
     */
    open fun `when`(type: String, pattern: String, callback: (Request) -> Response): IrcRouter
    {
        return `when`(buildRoute(type, pattern, callback)) as IrcRouter
    }

    override fun buildRoute(pattern: String, callback: (Request) -> Response): IrcRoute?
    {
        return buildRoute(CHANNEL_MESSAGE, pattern, callback)
    }

    open fun buildRoute(type: String, pattern: String, callback: (Request) -> Response): IrcRoute?
    {
        when (type)
        {
            PRIVATE_MESSAGE -> return PrivateMessageRoute(pattern, callback)
            CHANNEL_MESSAGE -> return ChannelMessageRoute(pattern, callback)
            PING -> return PingRoute(callback)
        }
        return null
    }

    companion object
    {
        @JvmStatic
        val MESSAGE: String = "PRIVMSG"
        @JvmStatic
        val PRIVATE_MESSAGE = "private"
        @JvmStatic
        val CHANNEL_MESSAGE = "channel"
        @JvmStatic
        val PING = "PING"
        /**
         * According RFC 2813, 005 means RPL_BOUNCE, but Undernet and Dalnet servers pushed a de facto
         * standard to use it as RPL_ISUPPORT code, which notes what modes server supports in particular.
         *
         * Though there is no official document on RPL_ISUPPORT, you can read about it in Brocklesby's
         * IRC support draft at [irc.org](http://www.irc.org/tech_docs/draft-brocklesby-irc-isupport-03.txt)
         */
        @JvmStatic
        val RPL_ISUPPRORT = "005" // According to RFC 2812 005 means RPL_BOUNCE, but servers use it as RPL_ISUPPORT?
    }
}