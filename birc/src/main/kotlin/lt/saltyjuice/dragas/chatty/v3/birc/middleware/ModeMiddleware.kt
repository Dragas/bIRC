package lt.saltyjuice.dragas.chatty.v3.birc.middleware

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.middleware.IrcMiddleware

abstract class ModeMiddleware(protected val settings: BIrcSettings) : IrcMiddleware()
{
    override val name: String = "MODE"
    abstract val mode: Int
    override fun before(request: Request): Boolean
    {
        return (settings.currentMode.and(mode)) == mode
    }

    override fun after(response: Response): Boolean
    {
        return (settings.currentMode.and(mode)) == mode
    }
}