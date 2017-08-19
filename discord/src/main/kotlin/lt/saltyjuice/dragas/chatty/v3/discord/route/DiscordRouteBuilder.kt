package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouteBuilder

open class DiscordRouteBuilder<Request : OPRequest<*>, Response : OPResponse<*>> : WebSocketRouteBuilder<Request, Response>()
{
    //private var typeChecker : (Any) -> Boolean = {true}
    override fun type(clazz: Class<*>): DiscordRouteBuilder<Request, Response>
    {
        return super.type(clazz) as DiscordRouteBuilder
    }

    override fun testCallback(callback: (Request) -> Boolean): DiscordRouteBuilder<Request, Response>
    {
        return super.testCallback(callback) as DiscordRouteBuilder
    }

    override fun callback(callback: (Request) -> Response?): DiscordRouteBuilder<Request, Response>
    {
        return super.callback(callback) as DiscordRouteBuilder
    }

    override fun middleware(name: String): DiscordRouteBuilder<Request, Response>
    {
        return super.middleware(name) as DiscordRouteBuilder
    }

    @Throws(NullPointerException::class)
    override fun build(): DiscordRoute<Request, Response>
    {
        return object : DiscordRoute<Request, Response>()
        {
            //override val typeChecker : (Any) -> Boolean = this@DiscordRouteBuilder.typeChecker
            override var testCallback: (Request) -> Boolean = this@DiscordRouteBuilder.mTestCallback ?: throw NullPointerException("Required")
                set(value)
                {

                }
            override var callback: (Request) -> Response? = this@DiscordRouteBuilder.mCallback ?: throw NullPointerException("Callback is necessary")
                set(value)
                {
                }
            override var middlewares: List<Middleware<Request, Response>> = this@DiscordRouteBuilder.mMiddlewares as List<Middleware<Request, Response>>
                set(value)
                {
                }
        }
    }
}