package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.DeletedMessages
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventMessageDeleteBulk : OPRequest<DeletedMessages>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "MESSAGE_DELETE_BULK"
    }
}