package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.RoleChanged
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildRoleCreate : OPRequest<RoleChanged>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_ROLE_CREATE"
    }
}