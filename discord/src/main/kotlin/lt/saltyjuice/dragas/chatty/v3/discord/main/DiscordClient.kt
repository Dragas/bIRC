package lt.saltyjuice.dragas.chatty.v3.discord.main

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.core.route.UsesControllers
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.adapter.CompressedDiscordAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.adapter.DiscordAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordInput
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordOutput
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.discord.route.DiscordRouter
import lt.saltyjuice.dragas.chatty.v3.websocket.main.WebSocketClient
import java.net.URI
import javax.websocket.ClientEndpointConfig

/**
 * Isn't any different from [WebSocketClient] besides having discord oriented calls for tyrus and chatty-websocket.
 * @see WebSocketClient
 * @see Client
 */
@UsesControllers(ConnectionController::class)
open class DiscordClient(protected val initResponse: GatewayInit) : WebSocketClient<String, OPRequest<*>, OPResponse<*>, String>()
{
    override val router: DiscordRouter = DiscordRouter()
    override val cec: ClientEndpointConfig = ClientEndpointConfig.Builder.create()
            .apply()
            {
                decoders(listOf(DiscordAdapter::class.java, CompressedDiscordAdapter::class.java))
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

    /**
     * Performs actions that are supposed to be handled after the connection has ended.
     */
    override fun onDisconnect()
    {
        ConnectionController.destroy()
    }
}