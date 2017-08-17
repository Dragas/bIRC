package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouteBuilder
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouter

open class DiscordRouter : WebSocketRouter<OPRequest<*>, OPResponse<*>>()
{
    open fun add(route: DiscordRouteBuilder<*, *>)
    {
        add(route as WebSocketRouteBuilder<OPRequest<*>, OPResponse<*>>)
    }

    /**
     * Discord API implementations should use [discordBuilder] instead, as it permits specifying what
     */
    override fun builder(): WebSocketRouteBuilder<OPRequest<*>, OPResponse<*>>
    {
        return discordBuilder()
    }

    /**
     * Returns a discord route builder, which can specify what
     */
    open fun <Request : OPRequest<*>, Response : OPResponse<*>> discordBuilder(): DiscordRouteBuilder<Request, Response>
    {
        return DiscordRouteBuilder()
    }
}