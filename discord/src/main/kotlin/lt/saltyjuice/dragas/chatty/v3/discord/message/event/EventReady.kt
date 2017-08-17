package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Ready
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventReady : OPRequest<Ready>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "READY"
    }
}