package lt.saltyjuice.dragas.chatty.v3.discord.controller

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse

/**
 * A wrapper for general methods that should be used in discord controllers.
 */
open class DiscordController : Controller<OPRequest<*>, OPResponse<*>>()
{
    /**
     * Permits writing response to client from controller.
     *
     * Note: Discord implementations should not use this method as it tries to push responses to websocket, instead of HTTP
     * as it's meant to. Instead, use [Utility.discordAPI]
     */
    override fun writeResponse(response: OPResponse<*>)
    {
        super.writeResponse(response)
    }
}