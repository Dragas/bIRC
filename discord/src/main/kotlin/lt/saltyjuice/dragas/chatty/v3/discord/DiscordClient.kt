package lt.saltyjuice.dragas.chatty.v3.discord

import lt.saltyjuice.dragas.chatty.v3.discord.adapter.DiscordAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordInput
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordOutput
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.main.WebSocketClient
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouter
import java.net.URI
import javax.websocket.ClientEndpointConfig

open class DiscordClient(protected val initResponse: GatewayInit) : WebSocketClient<String, OPRequest<*>, OPResponse<*>, String>()
{
    override val router: WebSocketRouter<OPRequest<*>, OPResponse<*>>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val cec: ClientEndpointConfig = ClientEndpointConfig.Builder.create()
            .apply()
            {
                decoders(listOf(DiscordAdapter::class.java))
                encoders(listOf(DiscordAdapter::class.java))
                //configurator(DiscordEndpointConfig())
            }
            .build()
    override val uri: URI = URI("${initResponse.url}/?v=${Settings.API_VERSION}&encoding=${Settings.API_ENCODING}")
    override val sin: DiscordInput by lazy()
    {
        DiscordEndpoint.instance
    }
    override val sout: DiscordOutput by lazy()
    {
        DiscordEndpoint.instance
    }
}