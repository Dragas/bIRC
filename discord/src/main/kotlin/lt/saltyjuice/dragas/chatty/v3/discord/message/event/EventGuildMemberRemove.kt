package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.ChangedMember
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildMemberRemove : OPRequest<ChangedMember>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_MEMBER_REMOVE"
    }
}