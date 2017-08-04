package lt.saltyjuice.dragas.chatty.v3.birc.middleware

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command

class SilentMiddleware(bIrcSettings: BIrcSettings) : ModeMiddleware(bIrcSettings)
{
    override val mode: Int = settings.getMode("silent")
    override val name: String = "SILENT"

    override fun after(response: Response): Boolean
    {
        return settings.currentMode != mode || response.command != Command.PRIVMSG.value
    }

    open fun after(responses: List<Response>): List<Response>
    {
        return responses.filter(this::after)
    }
}