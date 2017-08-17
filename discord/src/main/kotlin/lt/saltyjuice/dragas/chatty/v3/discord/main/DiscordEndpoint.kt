package lt.saltyjuice.dragas.chatty.v3.discord.main

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.adapter.DiscordAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
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
 * [WebSocketEndpoint] implementation for discord.
 *
 * Used to connect to discord API and use JSON encoding. Contains several methods that are called during connection lifecycle.
 */
open class DiscordEndpoint : WebSocketEndpoint<String, OPRequest<*>, OPResponse<*>, String>(), DiscordInput, DiscordOutput
{
    init
    {
        /*addMessageHandler(GatewayHello::class.java, this::onHello)
        addMessageHandler(GatewayAck::class.java, this::onGatewayAck)
        addMessageHandler(GatewayInvalid::class.java, this::onGatewayInvalid)
        addMessageHandler(GatewayReconnect::class.java, this::onGatewayReconnect)*/
    }

    override val adapter: DiscordAdapter by lazy()
    {
        DiscordAdapter.getInstance()
    }

    /**
     * A heartbeat job.
     *
     * Initialized by [onHello] call, which happens once when session begins. When the session closes, this job
     * should be cancelled. Preferably in [onClose] call.
     */
    @Deprecated("handled by connection controller")
    protected open var heartbeatJob: Job? = null
    /**
     * Used to hold a concurrent reference to last sequence number that was sent via requests, if available.
     * Since it should be included only if the number is positive, the default value is set to -1.
     */
    @Deprecated("Handled by connection controller")
    protected open val sequenceNumber: AtomicLong = AtomicLong(-1)

    override val baseClass: Class<OPRequest<*>> = OPRequest::class.java

    @Deprecated("Should be handled in controllers")
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

    override fun onOpen(session: Session, config: EndpointConfig)
    {
        super.onOpen(session, config)
    }

    @Deprecated("Should be handled in controllers")
    open fun onGatewayAck(request: GatewayAck)
    {

    }

    @Deprecated("Should be handled in controllers")
    open fun onGatewayInvalid(request: GatewayInvalid)
    {

    }

    @Deprecated("Should be handled in controllers")
    open fun onHeartbeat(session: Session)
    {
        var sequenceNumber: Long? = sequenceNumber.get()
        if (sequenceNumber == -1L)
            sequenceNumber = null
        writeResponse(GatewayHeartbeat(sequenceNumber))
    }

    @Deprecated("Should be handled in controllers")
    open fun onGatewayReconnect(request: GatewayReconnect)
    {

    }

    override fun onMessage(request: OPRequest<*>)
    {
        if (request.sequenceNumber != null)
            ConnectionController.setSequenceNumber(request.sequenceNumber!!)
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