package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.VoiceState
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventVoiceStateUpdate : OPRequest<VoiceState>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "VOICE_STATE_UPDATE"
    }
}