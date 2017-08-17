package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.TypingStart
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventTypingStart : OPRequest<TypingStart>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "TYPING_START"
    }
}