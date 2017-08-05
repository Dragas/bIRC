package lt.saltyjuice.dragas.chatty.v3.birc.middleware

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command

class SilentMiddleware(settings: BIrcSettings) : ModeMiddleware(settings, "SILENT", settings.getMode("silent"))
{

    override fun before(request: Request): Boolean
    {
        return true
    }

    override fun after(response: Response): Boolean
    {
        return settings.currentMode != mode || response.command != Command.PRIVMSG.value
    }

    open fun after(responses: List<Response>): List<Response>
    {
        return responses.filter(this::after)
    }
}