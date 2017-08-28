package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Presence

open class GatewayStatusUpdate(@Expose @SerializedName("d") override var data: Presence) : OPResponse<Presence>()
{
    @Expose
    @SerializedName("op")
    override val op: Int = 3
}