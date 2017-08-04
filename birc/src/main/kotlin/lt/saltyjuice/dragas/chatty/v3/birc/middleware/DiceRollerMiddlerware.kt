package lt.saltyjuice.dragas.chatty.v3.birc.middleware

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings

class DiceRollerMiddlerware(settings: BIrcSettings) : ModeMiddleware(settings)
{
    override val name: String = "DICEROLLAN"
    override val mode: Int = settings.getMode("diceroller")
}