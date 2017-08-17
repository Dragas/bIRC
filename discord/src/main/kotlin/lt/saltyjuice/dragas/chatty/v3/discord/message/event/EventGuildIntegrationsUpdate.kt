package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.GuildIntegrationUpdate
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventGuildIntegrationsUpdate : OPRequest<GuildIntegrationUpdate>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "GUILD_INTEGRATIONS_UPDATE"
    }
}