package lt.saltyjuice.dragas.chatty.v3.irc.routing

import lt.saltyjuice.dragas.chatty.v3.core.route.Router
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response


open class IrcRouter : Router<Request, Response>()
{
    override fun builder(): IrcRouteBuilder
    {
        return IrcRouteBuilder()
    }
}