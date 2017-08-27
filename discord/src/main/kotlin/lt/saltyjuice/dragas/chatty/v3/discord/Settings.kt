package lt.saltyjuice.dragas.chatty.v3.discord

object Settings
{
    val API_VERSION = 6
    val token = System.getenv("DISCORD_KET") ?: throw NullPointerException("DISCORD_KEY environmental variable must be present")
    val URL_LINK: String = "https://github.com/Dragas/bIRC"
    val API_ENCODING = "json"
    val BASE_URL = "https://discordapp.com/api/v$API_VERSION/"
    val VERSION = 1
}