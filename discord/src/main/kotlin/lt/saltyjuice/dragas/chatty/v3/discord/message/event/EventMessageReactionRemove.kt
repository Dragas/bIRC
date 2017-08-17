package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Reaction
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventMessageReactionRemove : OPRequest<Reaction>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "MESSAGE_REACTION_REMOVE"
    }
}