package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.CreatedGuild
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildDelete : OPRequest<CreatedGuild>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_DELETE"
    }
}