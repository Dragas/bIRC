package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.VoiceStateUpdate

class GatewayVoiceStateUpdate(@SerializedName("d") override var data: VoiceStateUpdate) : OPResponse<VoiceStateUpdate>()
{
    override val op: Int = 4
}