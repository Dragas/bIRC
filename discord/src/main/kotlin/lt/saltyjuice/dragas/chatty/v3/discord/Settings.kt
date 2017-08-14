package lt.saltyjuice.dragas.chatty.v3.discord

import com.google.gson.Gson
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

object Settings
{
    val API_VERSION = 6
    val token = "Whoops. Can't show this on christian repository."
    val URL_LINK: String = "https://github.com/Dragas/bIRC"
    val API_ENCODING = "json"
    val VERSION = 1
    fun getGatewayInitialResponse(): GatewayInit
    {
        val gson = Gson()
        val url = URL("https://discordapp.com/api/v$API_VERSION/gateway/bot")
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.addRequestProperty("Authorization", "Bot $token")
        connection.addRequestProperty("User-Agent", "DiscordBot ($URL_LINK, $VERSION)")
        connection.useCaches = false
        val sin = Scanner(BufferedReader(InputStreamReader(connection.inputStream)))
        val sb = StringBuilder()
        while (sin.hasNextLine())
        {
            sb.appendln(sin.nextLine())
        }
        return gson.fromJson(sb.toString(), GatewayInit::class.java)
    }
}