package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.VoiceStateUpdate

class GatewayVoiceStateUpdate(override var data: VoiceStateUpdate) : OPResponse<VoiceStateUpdate>()
{
    override val op: Int = 4
}