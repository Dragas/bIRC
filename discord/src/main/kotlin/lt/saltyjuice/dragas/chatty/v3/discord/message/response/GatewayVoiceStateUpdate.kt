package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.VoiceStateUpdate

open class GatewayVoiceStateUpdate(@Expose @SerializedName("d") override var data: VoiceStateUpdate) : OPResponse<VoiceStateUpdate>()
{
    @Expose
    @SerializedName("op")
    override val op: Int = 4
}