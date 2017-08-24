package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRoute
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouter

open class DiscordRouter : WebSocketRouter<OPRequest<*>, OPResponse<*>>()
{
    open fun add(route: DiscordRoute.DiscordRouteBuilder<*, *>)
    {
        add(route as WebSocketRoute.WebSocketRouteBuilder<OPRequest<*>, OPResponse<*>>)
    }

    /**
     * Discord API implementations should use [discordBuilder] instead, as it permits specifying what
     */
    override fun builder(): WebSocketRoute.WebSocketRouteBuilder<OPRequest<*>, OPResponse<*>>
    {
        return discordBuilder()
    }

    /**
     * Returns a discord route builder, which can specify what
     */
    open fun <Request : OPRequest<*>, Response : OPResponse<*>> discordBuilder(): DiscordRoute.DiscordRouteBuilder<Request, Response>
    {
        return DiscordRoute.DiscordRouteBuilder()
    }
}