package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class Status private constructor(private val value: String)
{
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    AFK("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline")
}