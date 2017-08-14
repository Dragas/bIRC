@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.discord

import kotlinx.coroutines.experimental.runBlocking
import org.glassfish.tyrus.core.TyrusEndpointWrapper
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.logging.Level
import java.util.logging.Logger


fun main(args: Array<String>) = runBlocking<Unit>
{
    Logger.getLogger(TyrusEndpointWrapper::class.java.name).level = Level.ALL
    val gatewayResponse = Settings.getGatewayInitialResponse()
    val discordClient = DiscordClient(gatewayResponse)
    discordClient.initialize()
    discordClient.connect()
    println("Press any key to stop")
    BufferedReader(InputStreamReader(System.`in`)).readLine()
}