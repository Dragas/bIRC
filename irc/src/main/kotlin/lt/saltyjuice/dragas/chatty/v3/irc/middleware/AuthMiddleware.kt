package lt.saltyjuice.dragas.chatty.v3.irc.middleware

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request


class AuthMiddleware : IrcMiddleware()
{
    override val name: String = "AUTH"

    override fun before(request: Request): Boolean
    {
        return request.arguments[0] == "AUTH"
    }
}