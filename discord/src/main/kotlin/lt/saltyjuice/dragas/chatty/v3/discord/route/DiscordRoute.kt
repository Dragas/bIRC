package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRoute

open class DiscordRoute<Request : OPRequest<*>, Response : OPResponse<*>> : WebSocketRoute<Request, Response>()
{
    open class DiscordRouteBuilder<Request : OPRequest<*>, Response : OPResponse<*>> : WebSocketRouteBuilder<Request, Response>()
    {
        override fun testCallback(callback: (Request) -> Boolean): DiscordRouteBuilder<Request, Response>
        {
            return super.testCallback(callback) as DiscordRouteBuilder
        }

        override fun callback(callback: (Request) -> Response?): DiscordRouteBuilder<Request, Response>
        {
            return super.callback(callback) as DiscordRouteBuilder
        }

        override fun after(clazz: Class<AfterMiddleware<Response>>): DiscordRouteBuilder<Request, Response>
        {
            return super.after(clazz) as DiscordRouteBuilder
        }

        override fun before(clazz: Class<BeforeMiddleware<Request>>): DiscordRouteBuilder<Request, Response>
        {
            return super.before(clazz) as DiscordRouteBuilder
        }

        /**
         * Implementations should return a raw route object which is later used in [adapt] to add all the callbacks, middlewares, etc.
         */
        override fun returnableRoute(): DiscordRoute<Request, Response>
        {
            return DiscordRoute()
        }
    }
}