package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Identify

open class GatewayIdentify(@Expose @SerializedName("d") override var data: Identify) : OPResponse<Identify>()
{
    @Expose
    @SerializedName("op")
    override val op: Int = 2
}