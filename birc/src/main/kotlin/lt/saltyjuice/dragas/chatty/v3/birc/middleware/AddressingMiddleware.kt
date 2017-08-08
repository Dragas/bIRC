package lt.saltyjuice.dragas.chatty.v3.birc.middleware

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.middleware.IrcMiddleware
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command

class AddressingMiddleware(private val settings: BIrcSettings) : IrcMiddleware()
{
    override val name: String
        get() = "ADDRESS" //To change initializer of created properties use File | Settings | File Templates.

    override fun before(request: Request): Boolean
    {
        return request.command == Command.PRIVMSG.value && request.arguments[1].startsWith("${ConnectionController.currentNickname}, ")
    }
}