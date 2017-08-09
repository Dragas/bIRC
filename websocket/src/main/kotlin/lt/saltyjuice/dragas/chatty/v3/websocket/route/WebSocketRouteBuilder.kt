package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware
import lt.saltyjuice.dragas.chatty.v3.core.route.RouteBuilder
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Request
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Response


/**
 * Wrapper for regular routebuilder in chatty core.
 *
 * This implementation returns Websocket routes, which can be used in websocket based applications.
 */
open class WebSocketRouteBuilder : RouteBuilder<Request, Response>()
{
    override fun build(): WebSocketRoute
    {
        return object : WebSocketRoute()
        {
            override var callback: (Request) -> Response? = this@WebSocketRouteBuilder.mCallback ?: throw NullPointerException("Callback is required.")
                set(value)
                {
                }
            override var middlewares: List<Middleware<Request, Response>> = this@WebSocketRouteBuilder.mMiddlewares as List<Middleware<Request, Response>>
                set(value)
                {
                }
            override var testCallback: (Request) -> Boolean = this@WebSocketRouteBuilder.mTestCallback ?: throw NullPointerException("TestCallback is required.")
                set(value)
                {
                }
        }
    }
}