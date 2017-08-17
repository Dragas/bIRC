package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouteBuilder

open class DiscordRouteBuilder : WebSocketRouteBuilder<OPRequest<*>, OPResponse<*>>()
{
    override fun type(clazz: Class<*>): DiscordRouteBuilder
    {
        return super.type(clazz) as DiscordRouteBuilder
    }

    override fun testCallback(callback: (OPRequest<*>) -> Boolean): DiscordRouteBuilder
    {
        return super.testCallback(callback) as DiscordRouteBuilder
    }

    override fun callback(callback: (OPRequest<*>) -> OPResponse<*>?): DiscordRouteBuilder
    {
        return super.callback(callback) as DiscordRouteBuilder
    }

    override fun middleware(name: String): DiscordRouteBuilder
    {
        return super.middleware(name) as DiscordRouteBuilder
    }

    @Throws(NullPointerException::class)
    override fun build(): DiscordRoute
    {
        return object : DiscordRoute()
        {
            override var callback: (OPRequest<*>) -> OPResponse<*>? = this@DiscordRouteBuilder.mCallback ?: throw NullPointerException("Callback is necessary")
                set(value)
                {
                }
            override var middlewares: List<Middleware<OPRequest<*>, OPResponse<*>>> = this@DiscordRouteBuilder.mMiddlewares as List<Middleware<OPRequest<*>, OPResponse<*>>>
                set(value)
                {
                }
            override var testCallback: (OPRequest<*>) -> Boolean = this@DiscordRouteBuilder.mTestCallback ?: throw NullPointerException("Test callback is necessary.")
                set(value)
                {
                }
        }
    }
}