package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.route.DiscordMiddleware

class MentionsMe : DiscordMiddleware()
{
    override fun before(request: OPRequest<*>): Boolean
    {
        if (request is EventMessageCreate)
        {
            val content = request.data!!
            return content.mentionsMe() &&
                    content.content.startsWith("<@${ConnectionController.getCurrentUserId()}>").apply()
                    {
                        content.content = content.content.replace("<@${ConnectionController.getCurrentUserId()}> ", "")
                    }
        }
        return false
    }
}

private fun Message.mentionsMe(): Boolean
{
    return this.mentionedUsers.find { ConnectionController.isMe(it) } != null
}
