package lt.saltyjuice.dragas.chatty.v3.discord.controller

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A wrapper for general methods that should be used in discord controllers.
 */
open class DiscordController : Controller<OPRequest<*>, OPResponse<*>>()
{
    open protected val messageCallback: Callback<Message> = object : Callback<Message>
    {
        override fun onFailure(call: Call<Message>, t: Throwable)
        {
            t.printStackTrace(System.err)
        }

        override fun onResponse(call: Call<Message>, response: Response<Message>)
        {
            println("message response: ${call.request().method()} --> ${response.code()} ")
        }
    }
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

    @JvmOverloads
    protected open fun writeResponse(channelId: String, message: String, callback: Callback<Message> = messageCallback)
    {
        Utility.discordAPI.createMessage(channelId, message).enqueue(callback)
    }
}