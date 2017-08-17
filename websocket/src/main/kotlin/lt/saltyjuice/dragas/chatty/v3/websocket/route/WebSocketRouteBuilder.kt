package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware
import lt.saltyjuice.dragas.chatty.v3.core.route.RouteBuilder


/**
 * Wrapper for regular routebuilder in chatty core.
 *
 * This implementation returns Websocket routes, which can be used in websocket based applications.
 */
open class WebSocketRouteBuilder<Request, Response> : RouteBuilder<Request, Response>()
{
    /**
     * Meant for explicit type handlers.
     *
     * Equivalent to calling testCallback { it.javaClass.isAssignableFrom(clazz) && clazz.isAssignableFrom(it.javaClass) }
     */
    open fun type(clazz: Class<*>): WebSocketRouteBuilder<Request, Response>
    {
        return testCallback({ it: Request ->
            it as Any
            it.javaClass.isAssignableFrom(clazz) && clazz.isAssignableFrom(it.javaClass)
        })
    }

    override fun testCallback(callback: (Request) -> Boolean): WebSocketRouteBuilder<Request, Response>
    {
        return super.testCallback(callback) as WebSocketRouteBuilder<Request, Response>
    }

    override fun callback(callback: (Request) -> Response?): WebSocketRouteBuilder<Request, Response>
    {
        return super.callback(callback) as WebSocketRouteBuilder<Request, Response>
    }

    override fun middleware(name: String): WebSocketRouteBuilder<Request, Response>
    {
        return super.middleware(name) as WebSocketRouteBuilder<Request, Response>
    }

    override fun build(): WebSocketRoute<Request, Response>
    {
        return object : WebSocketRoute<Request, Response>()
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