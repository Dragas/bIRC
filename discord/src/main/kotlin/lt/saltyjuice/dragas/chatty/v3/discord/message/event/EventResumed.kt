package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Resumed
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventResumed : OPRequest<Resumed>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "RESUMED"
    }
}