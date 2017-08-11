package lt.saltyjuice.dragas.chatty.v3.discord.message.request

import com.google.gson.annotations.SerializedName

/**
 * [OPCode] implementation, which is actually used when deserializing requests. This class' implementations specify
 * which payload does this request carry.
 */
abstract class OPRequest<D> : OPCode()
{
    @SerializedName("d")
    open var data: D? = null

    @SerializedName("s")
    open var sequenceNumber: Long? = null
}