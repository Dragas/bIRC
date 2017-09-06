package lt.saltyjuice.dragas.chatty.v3.discord.controller

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventChannelCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventGuildCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventGuildMemberAdd
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventReady
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.*
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
    fun onAck(request: GatewayAck): OPResponse<*>?
    {
        return null
    }

    @On(GatewayInvalid::class)
    fun onSessionInvalid(request: GatewayInvalid): OPResponse<*>?
    {
        return null
    }

    @On(GatewayReconnect::class)
    fun onReconnect(request: GatewayReconnect): OPResponse<*>?
    {
        return null
    }

    @On(EventReady::class)
    fun onReady(request: EventReady): OPResponse<*>?
    {
        readyEvent = request

        return null
    }

    @On(EventGuildCreate::class)
    fun onGuildCreate(request: EventGuildCreate): OPResponse<*>?
    {
        val data = request.data!!
        guilds[data.id] = data
        data.channels.forEach {
            channels[it.id] = it
            it.guildId = data.id
        }
        return null
    }

    @On(EventChannelCreate::class)
    fun onChannelCreate(request: EventChannelCreate): OPResponse<*>?
    {
        channels[request.data!!.id] = request.data!!
        return null
    }

    @On(EventGuildMemberAdd::class)
    fun onMemberAdd(request: EventGuildMemberAdd): OPResponse<*>?
    {
        val data = request.data!!
        val guild = guilds[data.guildId]!!
        guild.users.add(data)
        return null
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

        @JvmStatic
        private lateinit var readyEvent: EventReady

        @JvmStatic
        public fun isMe(anotherUser: User): Boolean
        {
            return getCurrentUserId() == anotherUser.id
        }

        @JvmStatic
        public fun getCurrentUserId(): String
        {
            return getCurrentUser().id
        }

        @JvmStatic
        public fun getCurrentUser(): User
        {
            return readyEvent.data!!.user!!
        }

        private val debugChannel = System.getenv("debug_channel_id")
        @JvmStatic
        public fun getUser(channelId: String, userId: String): Member?
        {
            val channel = channels[channelId]
            if (channel == null)
            {
                MessageBuilder().append("Warning: $channelId doesn't correspond to any channel.").send(debugChannel)
                return null
            }
            val guild = guilds[channel.guildId]
            if (guild == null)
            {
                MessageBuilder().append("Warning: channel with id ${channel.id} doesn't correspond to any guild.").send(debugChannel)
                return null
            }
            val member = guild.users.find { it.user.id == userId }
            if (member == null)
            {
                MessageBuilder().append("Warning: user with id $userId doesn't correspond to any member.").send(debugChannel)
                return null
            }
            return member
        }

        @JvmStatic
        private val guilds: HashMap<String, CreatedGuild> = HashMap()

        @JvmStatic
        private val channels: HashMap<String, Channel> = HashMap()
    }
}
