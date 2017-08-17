package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class VerificationLevel private constructor(private val value: Int)
{
    NONE(0),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    VERY_HIGH(4)
}