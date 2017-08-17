package lt.saltyjuice.dragas.chatty.v3.discord.controller

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.io.DiscordOutput
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Identify
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayAck
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayHello
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInvalid
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayReconnect
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.GatewayHeartbeat
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.GatewayIdentify
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.discord.route.DiscordRouter
import java.util.concurrent.atomic.AtomicLong

open class ConnectionController private constructor(private val output: DiscordOutput)
{
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
        output.writeResponse(GatewayHeartbeat(sequenceNumber))
    }

    fun onAck(request: GatewayAck): OPResponse<*>?
    {
        return null
    }

    fun onSessionInvalid(request: GatewayInvalid): OPResponse<*>?
    {
        return null
    }

    fun onReconnect(request: GatewayReconnect): OPResponse<*>?
    {
        return null
    }


    companion object
    {
        @JvmStatic
        private val sequenceNumber: AtomicLong = AtomicLong(-1)

        @JvmStatic
        private var heartbeatJob: Job? = null

        @JvmStatic
        private lateinit var instance: ConnectionController

        @JvmStatic
        fun initialize(router: DiscordRouter, output: DiscordOutput)
        {
            instance = ConnectionController(output)
            router.add(router.discordBuilder<GatewayHello, GatewayIdentify>().apply {
                this.callback(instance::onHello)
                this.type(GatewayHello::class.java)
            })
            router.add(router.discordBuilder<GatewayAck, OPResponse<*>>().apply {
                this.callback(instance::onAck)
                this.type(GatewayAck::class.java)
            })
            router.add(router.discordBuilder<GatewayInvalid, OPResponse<*>>().apply {
                this.callback(instance::onSessionInvalid)
                this.type(GatewayInvalid::class.java)
            })
            router.add(router.discordBuilder<GatewayReconnect, OPResponse<*>>().apply {
                this.callback(instance::onReconnect)
                this.type(GatewayReconnect::class.java)
            })
        }

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
