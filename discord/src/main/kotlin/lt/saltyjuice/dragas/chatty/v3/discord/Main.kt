@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.discord

import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.discord.api.API
import lt.saltyjuice.dragas.chatty.v3.discord.main.DiscordClient


fun main(args: Array<String>) = runBlocking<Unit>
{
    val gatewayResponse = API.discordAPI.gatewayInit().execute().body()!!
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