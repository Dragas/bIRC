package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.ChannelPinUpdate
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventChannelPinsUpdate : OPRequest<ChannelPinUpdate>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "CHANNEL_PINS_UPDATE"
    }
}