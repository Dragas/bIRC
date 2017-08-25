package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.route.Route


/**
 * WebSocketRoute wrapper for regular routes in chatty/core.
 *
 * @see Route
 */
open class WebSocketRoute<Request, Response> : Route<Request, Response>()
{

    /**
     * Wrapper for regular routebuilder in chatty core.
     *
     * This implementation returns Websocket routes, which can be used in websocket based applications.
     */
    open class WebSocketRouteBuilder<Request, Response> : Route.Builder<Request, Response>()
    {
        override fun before(clazz: Class<BeforeMiddleware<Request>>): WebSocketRouteBuilder<Request, Response>
        {
            return super.before(clazz) as WebSocketRouteBuilder
        }

        override fun after(clazz: Class<AfterMiddleware<Response>>): WebSocketRouteBuilder<Request, Response>
        {
            return super.after(clazz) as WebSocketRouteBuilder
        }

        override fun description(string: String): WebSocketRouteBuilder<Request, Response>
        {
            return super.description(string) as WebSocketRouteBuilder
        }

        override fun adapt(route: Route<Request, Response>): WebSocketRoute<Request, Response>
        {
            return super.adapt(route) as WebSocketRoute
        }

        override fun returnableRoute(): WebSocketRoute<Request, Response>
        {
            return WebSocketRoute()
        }

        override fun testCallback(callback: (Request) -> Boolean): WebSocketRouteBuilder<Request, Response>
        {
            return super.testCallback(callback) as WebSocketRouteBuilder<Request, Response>
        }

        override fun callback(callback: (Request) -> Response?): WebSocketRouteBuilder<Request, Response>
        {
            return super.callback(callback) as WebSocketRouteBuilder<Request, Response>
        }


    }
}