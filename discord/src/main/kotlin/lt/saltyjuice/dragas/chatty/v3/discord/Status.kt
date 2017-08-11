package lt.saltyjuice.dragas.chatty.v3.discord

enum class Status(val value: String)
{
    ONLINE("online"),
    DO_NOT_DISTURB("dnd"),
    AFK("idle"),
    INVISIBLE("invisible"),
    OFFLINE("offline")
}