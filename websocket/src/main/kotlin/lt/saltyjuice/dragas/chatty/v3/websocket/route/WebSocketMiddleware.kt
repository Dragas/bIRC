package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware

/**
 * WebSocket wrapper for usual middleware.
 *
 * @see BeforeMiddleware
 * @see AfterMiddleware
 */
open class WebSocketMiddleware<Request, Response> : BeforeMiddleware<Request>, AfterMiddleware<Response>
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