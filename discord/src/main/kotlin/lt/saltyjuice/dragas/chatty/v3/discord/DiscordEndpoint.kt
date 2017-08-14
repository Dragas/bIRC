package lt.saltyjuice.dragas.chatty.v3.discord

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.discord.adapter.DiscordAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordInput
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordOutput
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Identify
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.*
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.GatewayHeartbeat
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.GatewayIdentify
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.main.WebSocketEndpoint
import java.util.concurrent.atomic.AtomicLong
import javax.websocket.CloseReason
import javax.websocket.EndpointConfig
import javax.websocket.Session

/**
 * WebSocketEndpoint implementation for discord.
 */
open class DiscordEndpoint : WebSocketEndpoint<String, OPRequest<*>, OPResponse<*>, String>(), DiscordInput, DiscordOutput
{
    override val adapter: DiscordAdapter by lazy()
    {
        DiscordAdapter.getInstance()
    }

    var heartbeatJob: Job? = null
    var sequenceNumber: AtomicLong = AtomicLong(-1)
    override val baseClass: Class<OPRequest<*>> = OPRequest::class.java

    override fun onOpen(session: Session, config: EndpointConfig)
    {
        addMessageHandler(GatewayHello::class.java, this::onHello)
        addMessageHandler(GatewayAck::class.java, this::onGatewayAck)
        addMessageHandler(GatewayInvalid::class.java, this::onGatewayInvalid)
        addMessageHandler(GatewayReconnect::class.java, this::onGatewayReconnect)
        super.onOpen(session, config)
        //session.addMessageHandler()

    }

    open fun onHello(request: GatewayHello)
    {
        val interval = request.data!!.heartBeatInterval
        if (request.sequenceNumber != null)
            sequenceNumber.set(request.sequenceNumber!!)

        val identify = Identify()
        identify.apply()
        {
            token = Settings.token
            threshold = 50
            shard = arrayListOf(0, 1)
        }
        writeResponse(GatewayIdentify(identify))
        heartbeatJob = launch(CommonPool)
        {
            while (true)
            {
                onHeartbeat(session!!)
                delay(interval)
            }
        }
    }

    open fun onGatewayAck(request: GatewayAck)
    {

    }

    open fun onGatewayInvalid(request: GatewayInvalid)
    {

    }

    open fun onHeartbeat(session: Session)
    {
        var sequenceNumber: Long? = sequenceNumber.get()
        if (sequenceNumber == -1L)
            sequenceNumber = null
        writeResponse(GatewayHeartbeat(sequenceNumber))
    }

    open fun onGatewayReconnect(request: GatewayReconnect)
    {

    }

    override fun onMessage(request: OPRequest<*>)
    {
        super.onMessage(request)
        if (request.sequenceNumber != null)
            sequenceNumber.set(request.sequenceNumber!!)
    }

    override fun onClose(session: Session, reason: CloseReason)
    {
        super.onClose(session, reason)
        heartbeatJob?.cancel()
        heartbeatJob = null
    }

    companion object
    {
        @JvmStatic
        val instance = DiscordEndpoint()
    }
}