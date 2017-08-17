package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventMessageCreate : OPRequest<Message>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "MESSAGE_CREATE"
    }
}