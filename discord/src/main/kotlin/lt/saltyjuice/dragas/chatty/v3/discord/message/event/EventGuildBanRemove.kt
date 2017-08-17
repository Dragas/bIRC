package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.GuildBan
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildBanRemove : OPRequest<GuildBan>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_BAN_REMOVE"
    }
}