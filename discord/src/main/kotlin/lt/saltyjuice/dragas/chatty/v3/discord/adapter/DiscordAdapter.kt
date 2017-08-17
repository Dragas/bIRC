package lt.saltyjuice.dragas.chatty.v3.discord.adapter

import com.google.gson.Gson
import lt.saltyjuice.dragas.chatty.v3.discord.api.API
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.*
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.*
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.adapter.WebSocketAdapter
import javax.websocket.Decoder
import javax.websocket.Encoder
import javax.websocket.EndpointConfig

/**
 * Discord adapter.
 *
 * As implementation requires, this adapter uses GSON to deserialize JSON based messages.
 */
open class DiscordAdapter : WebSocketAdapter<String, OPRequest<*>, OPResponse<*>, String>(), Decoder.Text<OPRequest<*>>, Encoder.Text<OPResponse<*>>
{
    protected open val decodableOPCodes: Array<Int> = arrayOf(OPCode.DISPATCH, OPCode.HEARTBEAT_ACK, OPCode.HELLO, OPCode.INVALID_SESSION, OPCode.RECONNECT)
    protected open val gson: Gson by lazy()
    {
        API.gson
    }
    init
    {
        defaultInstance = this
    }

    /**
     * Encode the given object into a String.

     * @param object the object being encoded.
     * *
     * @return the encoded object as a string.
     */
    override fun encode(response: OPResponse<*>): String
    {
        return serialize(response)
    }


    /**
     * This method is called with the endpoint configuration object of the
     * endpoint this decoder is intended for when
     * it is about to be brought into service.

     * @param config the endpoint configuration object when being brought into
     * * service
     */
    override fun init(config: EndpointConfig)
    {
        gson
    }

    /**
     * Answer whether the given String can be decoded into an object of type T.

     * @param s the string being tested for decodability.
     * @return whether this decoder can decoded the supplied string.
     */
    override fun willDecode(s: String): Boolean
    {
        val opRequest = getAsOpCode(s)

        return opRequest.opCode in decodableOPCodes
    }

    /**
     * Decode the given String into an object of type T.

     * @param s string to be decoded.
     * *
     * @return the decoded message as an object of type T
     */
    override fun decode(s: String): OPRequest<*>
    {
        println("Request => $s")
        return deserialize(s)
    }

    override fun deserialize(block: String): OPRequest<*>
    {
        val opRequest = getAsOpCode(block)
        val request = getFromJson(opRequest, block)
        return request//return gson.fromJson(block, typeOfT)
    }

    override fun serialize(any: OPResponse<*>): String
    {
        val string = gson.toJson(any)
        println("Response <= $string")
        return string
    }

    override fun destroy()
    {

    }

    open fun getAsOpCode(s: String): OPCode
    {
        val opRequest = gson.fromJson<OPCode>(s, OPCode::class.java)
        return opRequest
    }

    open fun getFromJson(opCode: OPCode, block: String): OPRequest<*>
    {
        when (opCode.opCode)
        {
            OPCode.RECONNECT -> return gson.fromJson<GatewayReconnect>(block, GatewayReconnect::class.java)
            OPCode.INVALID_SESSION -> return gson.fromJson<GatewayInvalid>(block, GatewayInvalid::class.java)
            OPCode.HELLO -> return gson.fromJson<GatewayHello>(block, GatewayHello::class.java)
            OPCode.HEARTBEAT_ACK -> return gson.fromJson<GatewayAck>(block, GatewayAck::class.java)
        }

        //println("some event happened. ${opCode.eventName}")

        when (opCode.eventName)
        {
            EventChannelCreate.EVENT_NAME -> return gson.fromJson<EventChannelCreate>(block, EventChannelCreate::class.java)
            EventChannelDelete.EVENT_NAME -> return gson.fromJson<EventChannelDelete>(block, EventChannelDelete::class.java)
            EventChannelPinsUpdate.EVENT_NAME -> return gson.fromJson<EventChannelPinsUpdate>(block, EventChannelPinsUpdate::class.java)
            EventChannelUpdate.EVENT_NAME -> return gson.fromJson<EventChannelUpdate>(block, EventChannelUpdate::class.java)
            EventGuildBanAdd.EVENT_NAME -> return gson.fromJson<EventGuildBanAdd>(block, EventGuildBanAdd::class.java)
            EventGuildBanRemove.EVENT_NAME -> return gson.fromJson<EventGuildBanRemove>(block, EventGuildBanRemove::class.java)
            EventGuildCreate.EVENT_NAME -> return gson.fromJson<EventGuildCreate>(block, EventGuildCreate::class.java)
            EventGuildDelete.EVENT_NAME -> return gson.fromJson<EventGuildDelete>(block, EventGuildDelete::class.java)
            EventGuildEmojisUpdate.EVENT_NAME -> return gson.fromJson<EventGuildEmojisUpdate>(block, EventGuildEmojisUpdate::class.java)
            EventGuildIntegrationsUpdate.EVENT_NAME -> return gson.fromJson<EventGuildIntegrationsUpdate>(block, EventGuildIntegrationsUpdate::class.java)
            EventGuildMemberAdd.EVENT_NAME -> return gson.fromJson<EventGuildMemberAdd>(block, EventGuildMemberAdd::class.java)
            EventGuildMemberRemove.EVENT_NAME -> return gson.fromJson<EventGuildMemberRemove>(block, EventGuildMemberRemove::class.java)
            EventGuildMembersChunk.EVENT_NAME -> return gson.fromJson<EventGuildMembersChunk>(block, EventGuildMembersChunk::class.java)
            EventGuildMemberUpdate.EVENT_NAME -> return gson.fromJson<EventGuildMemberUpdate>(block, EventGuildMemberUpdate::class.java)
            EventGuildRoleCreate.EVENT_NAME -> return gson.fromJson<EventGuildRoleCreate>(block, EventGuildRoleCreate::class.java)
            EventGuildRoleDelete.EVENT_NAME -> return gson.fromJson<EventGuildRoleDelete>(block, EventGuildRoleDelete::class.java)
            EventGuildRoleUpdate.EVENT_NAME -> return gson.fromJson<EventGuildRoleUpdate>(block, EventGuildRoleUpdate::class.java)
            EventMessageCreate.EVENT_NAME -> return gson.fromJson<EventMessageCreate>(block, EventMessageCreate::class.java)
            EventMessageUpdate.EVENT_NAME -> return gson.fromJson<EventMessageUpdate>(block, EventMessageUpdate::class.java)
            EventMessageDelete.EVENT_NAME -> return gson.fromJson<EventMessageDelete>(block, EventMessageDelete::class.java)
            EventMessageDeleteBulk.EVENT_NAME -> return gson.fromJson<EventMessageDeleteBulk>(block, EventMessageDeleteBulk::class.java)
            EventMessageReactionAdd.EVENT_NAME -> return gson.fromJson<EventMessageReactionAdd>(block, EventMessageReactionAdd::class.java)
            EventMessageReactionRemove.EVENT_NAME -> return gson.fromJson<EventMessageReactionRemove>(block, EventMessageReactionRemove::class.java)
            EventMessageReactionRemoveAll.EVENT_NAME -> return gson.fromJson<EventMessageReactionRemoveAll>(block, EventMessageReactionRemoveAll::class.java)
            EventPresenceUpdate.EVENT_NAME -> return gson.fromJson<EventPresenceUpdate>(block, EventPresenceUpdate::class.java)
            EventReady.EVENT_NAME -> return gson.fromJson<EventReady>(block, EventReady::class.java)
            EventResumed.EVENT_NAME -> return gson.fromJson<EventResumed>(block, EventResumed::class.java)
            EventTypingStart.EVENT_NAME -> return gson.fromJson<EventTypingStart>(block, EventTypingStart::class.java)
            EventVoiceServerUpdate.EVENT_NAME -> return gson.fromJson<EventVoiceServerUpdate>(block, EventVoiceServerUpdate::class.java)
            EventUserUpdate.EVENT_NAME -> return gson.fromJson<EventUserUpdate>(block, EventUserUpdate::class.java)
            EventVoiceStateUpdate.EVENT_NAME -> return gson.fromJson<EventVoiceStateUpdate>(block, EventVoiceStateUpdate::class.java)
            EventWebhooksUpdate.EVENT_NAME -> return gson.fromJson<EventWebhooksUpdate>(block, EventWebhooksUpdate::class.java)
        }
        throw IllegalStateException("Undeclared request opcode/event combo: ${opCode.opCode} - ${opCode.eventName}")
    }

    companion object
    {
        @JvmStatic
        private lateinit var defaultInstance: DiscordAdapter

        @JvmStatic
        fun getInstance(): DiscordAdapter
        {
            return defaultInstance
        }
    }
}