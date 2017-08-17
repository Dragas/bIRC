package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class ChannelType private constructor(private val value: Int)
{
    GUILD_TEXT(0),
    DM(1),
    GUILD_VOICE(2),
    GROUP_DM(3),
    GUILD_CATEGORY(4)
}