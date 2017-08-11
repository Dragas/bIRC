package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Router


open class WebSocketRouter<Request, Response> : Router<Request, Response>()
{
    override fun builder(): WebSocketRouteBuilder<Request, Response>
    {
        return WebSocketRouteBuilder()
    }
}