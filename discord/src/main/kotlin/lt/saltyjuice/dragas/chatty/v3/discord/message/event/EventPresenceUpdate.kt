package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.PresenceUpdate
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventPresenceUpdate : OPRequest<PresenceUpdate>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "PRESENCE_UPDATE"
    }
}