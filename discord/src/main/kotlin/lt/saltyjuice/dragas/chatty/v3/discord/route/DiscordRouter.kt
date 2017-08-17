package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouter

open class DiscordRouter : WebSocketRouter<OPRequest<*>, OPResponse<*>>()
{
    override fun builder(): DiscordRouteBuilder
    {
        return DiscordRouteBuilder()
    }
}