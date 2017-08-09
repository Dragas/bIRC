package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.route.RouteBuilder
import lt.saltyjuice.dragas.chatty.v3.core.route.Router
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Request
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Response


open class WebSocketRouter : Router<Request, Response>()
{
    override fun builder(): RouteBuilder<Request, Response>
    {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}