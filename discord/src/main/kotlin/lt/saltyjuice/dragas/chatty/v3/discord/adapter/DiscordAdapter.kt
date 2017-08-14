package lt.saltyjuice.dragas.chatty.v3.discord.adapter

import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
    open val decodableOPCodes: Array<Int> = arrayOf(0, 11, 10, 9, 7)

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

    protected open val gson: Gson by lazy()
    {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.serializeNulls()
        gsonBuilder.create()
    }

    init
    {
        //println("Am I real? $this")
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
        //println("Am i initialized? $this")
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
        //0 -> return getType(GatewayIdentify::class.java)
            7 -> return gson.fromJson<GatewayReconnect>(block, GatewayReconnect::class.java)
            9 -> return gson.fromJson<GatewayInvalid>(block, GatewayInvalid::class.java)
            10 -> return gson.fromJson<GatewayHello>(block, GatewayHello::class.java)
            11 -> return gson.fromJson<GatewayAck>(block, GatewayAck::class.java)
        }
        if (opCode.opCode == 0)
        {
            println("some event happened. ${opCode.eventName}")
        }
        throw IllegalStateException("Undeclared request opcode: ${opCode.opCode}")
        /*when(opCode.eventName)
        {

        }*/
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