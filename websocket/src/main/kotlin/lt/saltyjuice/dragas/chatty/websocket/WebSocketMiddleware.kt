package lt.saltyjuice.dragas.chatty.websocket

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.websocket.message.Request
import lt.saltyjuice.dragas.chatty.websocket.message.Response

open class WebSocketMiddleware : BeforeMiddleware<Request>, AfterMiddleware<Response>
{
    override fun before(request: Request): Boolean
    {
        return true
    }

    override fun after(response: Response): Boolean
    {
        return true
    }
}