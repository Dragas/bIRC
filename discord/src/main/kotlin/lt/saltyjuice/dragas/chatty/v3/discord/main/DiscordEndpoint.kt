package lt.saltyjuice.dragas.chatty.v3.discord.main

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.discord.adapter.DiscordAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordInput
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordOutput
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.main.WebSocketEndpoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.websocket.CloseReason
import javax.websocket.EndpointConfig
import javax.websocket.Session

/**
 * [WebSocketEndpoint] implementation for discord.
 *
 * Used to connect to discord API and use JSON encoding. Contains several methods that are called during connection lifecycle.
 */
open class DiscordEndpoint : WebSocketEndpoint<String, OPRequest<*>, OPResponse<*>, String>(), DiscordInput, DiscordOutput
{
    override val adapter: DiscordAdapter by lazy()
    {
        DiscordAdapter.getInstance()
    }

    override val baseClass: Class<OPRequest<*>> = OPRequest::class.java

    override fun onOpen(session: Session, config: EndpointConfig)
    {
        super.onOpen(session, config)
    }

    override fun onMessage(request: OPRequest<*>)
    {
        if (request.sequenceNumber != null)
            ConnectionController.setSequenceNumber(request.sequenceNumber!!)
    }


    override fun onClose(session: Session, reason: CloseReason)
    {
        super.onClose(session, reason)
    }

    companion object
    {
        @JvmStatic
        val instance = DiscordEndpoint()

        /**
         * Starts typing to particular channel.
         */
        @JvmOverloads
        @JvmStatic
        fun startTyping(channelId: String, callback: Callback<Any> = emptyCallback)
        {
            cancelTyping(channelId)
            typingMap[channelId] = launch(CommonPool)
            {
                while (true)
                {
                    Utility.discordAPI.triggerTypingIndicator(channelId).enqueue(callback)
                    delay(10000)
                }
            }
        }

        /**
         * Stops typing to particular channel.
         */
        @JvmStatic
        fun cancelTyping(channelId: String)
        {
            typingMap.remove(channelId)?.cancel()
        }

        @JvmStatic
        private val typingMap: MutableMap<String, Job> = mutableMapOf()

        @JvmStatic
        val emptyCallback: Callback<Any> = object : Callback<Any>
        {
            override fun onFailure(call: Call<Any>, t: Throwable)
            {
                t.printStackTrace(System.err)
            }

            override fun onResponse(call: Call<Any>, response: Response<Any>)
            {
                println("response is successful: ${response.isSuccessful}")
            }
        }
    }
}