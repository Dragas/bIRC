package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.RoleDeleted
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildRoleDelete : OPRequest<RoleDeleted>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_ROLE_DELETE"
    }
}