package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.MessageChannelPair
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventMessageReactionRemoveAll : OPRequest<MessageChannelPair>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "MESSAGE_REACTION_REMOVE_ALL"
    }
}