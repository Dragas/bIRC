package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class MFALevel private constructor(private val value: Int)
{
    NONE(0),
    ELEVATED(1)
}