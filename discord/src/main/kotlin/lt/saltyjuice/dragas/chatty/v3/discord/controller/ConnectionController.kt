package lt.saltyjuice.dragas.chatty.v3.discord.controller

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Identify
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayAck
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayHello
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInvalid
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayReconnect
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.GatewayHeartbeat
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.GatewayIdentify
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import java.util.concurrent.atomic.AtomicLong

open class ConnectionController : DiscordController()
{
    @On(GatewayHello::class)
    @When("onAllRequests")
    open fun onHello(request: GatewayHello): GatewayIdentify
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

        heartbeatJob = launch(CommonPool)
        {
            while (true)
            {
                onHeartbeat()
                delay(interval)
            }
        }
        return GatewayIdentify(identify)
    }

    private fun onHeartbeat()
    {
        var sequenceNumber: Long? = sequenceNumber.get()
        if (sequenceNumber == -1L)
            sequenceNumber = null
        writeResponse(GatewayHeartbeat(sequenceNumber))
    }

    @On(GatewayAck::class)
    @When("onAllRequests")
    fun onAck(request: GatewayAck): OPResponse<*>?
    {
        return null
    }

    @On(GatewayInvalid::class)
    @When("onAllRequests")
    fun onSessionInvalid(request: GatewayInvalid): OPResponse<*>?
    {
        return null
    }

    @On(GatewayReconnect::class)
    @When("onAllRequests")
    fun onReconnect(request: GatewayReconnect): OPResponse<*>?
    {
        return null
    }

    fun onAllRequests(request: Any): Boolean
    {
        return true
    }


    companion object
    {
        @JvmStatic
        private val sequenceNumber: AtomicLong = AtomicLong(-1)

        @JvmStatic
        private var heartbeatJob: Job? = null

        @JvmStatic
        fun setSequenceNumber(number: Long)
        {
            sequenceNumber.set(number)
        }

        @JvmStatic
        fun destroy()
        {
            heartbeatJob?.cancel()
            heartbeatJob = null
        }
    }
}
