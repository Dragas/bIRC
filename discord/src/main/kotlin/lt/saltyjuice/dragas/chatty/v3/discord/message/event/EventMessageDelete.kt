package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.DeletedMessage
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventMessageDelete : OPRequest<DeletedMessage>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "MESSAGE_DELETE"
    }
}