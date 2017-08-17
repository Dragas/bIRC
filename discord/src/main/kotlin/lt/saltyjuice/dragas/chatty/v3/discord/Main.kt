@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.discord

import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.discord.main.DiscordClient
import org.glassfish.tyrus.core.TyrusEndpointWrapper
import java.util.logging.Level
import java.util.logging.Logger


fun main(args: Array<String>) = runBlocking<Unit>
{
    Logger.getLogger(TyrusEndpointWrapper::class.java.name).level = Level.ALL
    val gatewayResponse = Settings.getGatewayInitialResponse()
    val discordClient = DiscordClient(gatewayResponse)
    discordClient.initialize()
    discordClient.connect()
    discordClient.onConnect()
    while (discordClient.isConnected())
    {
        discordClient.run()
    }
    discordClient.disconnect()
    discordClient.onDisconnect()
}