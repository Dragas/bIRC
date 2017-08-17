package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class OverwriteType private constructor(private val value: String)
{
    ROLE("role"),
    MEMBER("member")
}