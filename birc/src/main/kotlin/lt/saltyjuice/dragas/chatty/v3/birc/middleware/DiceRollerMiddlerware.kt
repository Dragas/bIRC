package lt.saltyjuice.dragas.chatty.v3.birc.middleware

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings

@Deprecated("Use your regular mode initializer instead")
class DiceRollerMiddlerware(settings: BIrcSettings) : ModeMiddleware(settings, "DICEROLLAN", settings.getMode("diceroller"))
{
    /*override val name: String = "DICEROLLAN"
    override val mode: Int = settings.getMode("diceroller")*/
}