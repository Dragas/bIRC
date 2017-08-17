package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class NotificationLevel private constructor(private val value: Int)
{
    ALL_MESSAGES(0),
    ONLY_MENTIONS(1)
}