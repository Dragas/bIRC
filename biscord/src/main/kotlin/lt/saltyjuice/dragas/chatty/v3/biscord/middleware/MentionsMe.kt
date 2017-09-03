package lt.saltyjuice.dragas.chatty.v3.biscord.middleware

import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.route.DiscordMiddleware

class MentionsMe : DiscordMiddleware()
{
    override fun before(request: OPRequest<*>): Boolean
    {
        if (request is EventMessageCreate)
        {
            val content = request.data!!
            val doesMentionMe = content.mentionsMe()
            val startsWithMe = content.content.run { startsWith("<@${ConnectionController.getCurrentUserId()}>") || startsWith("<@!${ConnectionController.getCurrentUserId()}>") }
            if (startsWithMe)
            {
                content.content = content.content.replaceFirst(Regex("<@!?${ConnectionController.getCurrentUserId()}>"), "")
                if (content.content.startsWith(" "))
                    content.content = content.content.replaceFirst(" ", "")
            }
            return doesMentionMe && startsWithMe
        }
        return false
    }
}
