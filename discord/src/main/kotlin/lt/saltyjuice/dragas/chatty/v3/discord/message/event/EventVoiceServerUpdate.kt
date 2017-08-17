package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.VoiceServerUpdate
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventVoiceServerUpdate : OPRequest<VoiceServerUpdate>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "VOICE_SERVER_UPDATE"
    }
}