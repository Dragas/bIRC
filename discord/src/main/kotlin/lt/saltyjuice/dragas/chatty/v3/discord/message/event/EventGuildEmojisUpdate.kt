package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.EmojisUpdate
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildEmojisUpdate : OPRequest<EmojisUpdate>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_EMOJIS_UPDATE"
    }
}