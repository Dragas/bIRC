package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Guild
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildUpdate : OPRequest<Guild>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_UPDATE"
    }
}