package lt.saltyjuice.dragas.chatty.v3.discord.message.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Base class for all discord based requests. This is used to determine whether or not websocket request
 * can be deserialized as particular request, which is later used down in the application pipeline.
 */
open class OPCode
{
    @Expose
    @SerializedName("op")
    open var opCode: Int = DEFAULT_OP_CODE

    @Expose
    @SerializedName("t")
    open var eventName: String? = null

    companion object
    {
        @JvmStatic
        val DEFAULT_OP_CODE = -1

        @JvmStatic
        val DISPATCH = 0

        @JvmStatic
        val HEARTBEAT_ACK = 11

        @JvmStatic
        val HELLO = 10

        @JvmStatic
        val INVALID_SESSION = 9

        @JvmStatic
        val RECONNECT = 7
    }
}