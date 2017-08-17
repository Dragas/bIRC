package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Channel
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventChannelCreate : OPRequest<Channel>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "CHANNEL_CREATE"
    }
}