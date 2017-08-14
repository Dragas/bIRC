package lt.saltyjuice.dragas.chatty.v3.discord.message.request

import com.google.gson.annotations.SerializedName

/**
 * Base class for all discord based requests. This is used to determine whether or not websocket request
 * can be deserialized as particular request, which is later used down in the application pipeline.
 */
open class OPCode
{
    @SerializedName("op")
    open var opCode: Int = DEFAULT_OP_CODE

    @SerializedName("t")
    open var eventName: String? = null

    companion object
    {
        @JvmStatic
        val DEFAULT_OP_CODE = -1
    }
}