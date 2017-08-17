package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class FilterLevel private constructor(private val value: Int)
{
    DISABLED(0),
    MEMBERS_WITHOUT_ROLES(1),
    ALL_MEMBERS(2)
}