package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Presence

open class GatewayStatusUpdate(@SerializedName("d") override var data: Presence) : OPResponse<Presence>()
{
    override val op: Int = 3
}