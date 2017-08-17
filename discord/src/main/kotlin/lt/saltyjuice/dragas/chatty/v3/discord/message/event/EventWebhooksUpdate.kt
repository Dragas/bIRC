package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.GuildChannelPair
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

class EventWebhooksUpdate : OPRequest<GuildChannelPair>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "WEBHOOKS_UPDATE"
    }
}