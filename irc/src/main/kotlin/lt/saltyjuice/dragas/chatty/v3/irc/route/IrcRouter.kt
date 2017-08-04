package lt.saltyjuice.dragas.chatty.v3.irc.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Router
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response

/**
 * An IRC implementation of core router.
 *
 * Works like your regular router from core, except that it returns an IrcRouteBuilder when building routes.
 *
 * @see Router
 */
open class IrcRouter : Router<Request, Response>()
{
    override fun builder(): IrcRouteBuilder
    {
        return IrcRouteBuilder()
    }
}