package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.MemberChunk
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildMembersChunk : OPRequest<MemberChunk>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_MEMBERS_CHUNK"
    }
}