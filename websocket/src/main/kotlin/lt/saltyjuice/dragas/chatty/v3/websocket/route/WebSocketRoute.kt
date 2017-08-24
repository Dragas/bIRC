package lt.saltyjuice.dragas.chatty.v3.websocket.route

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
        /**
         * Implementations should return a raw route object which is later used in [adapt] to add all the callbacks, middlewares, etc.
         */
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