package lt.saltyjuice.dragas.chatty.v3.biscord

import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility

fun main(args: Array<String>) = runBlocking<Unit>
{
    Utility.okHttpBuilder.addInterceptor(HeaderInterceptor(Pair("X-Requested-With", "XMLHttpRequest")))
    val gatewayResponse = Utility.discordAPI.gatewayInit().execute().body()!!
    val discordClient = BiscordClient(gatewayResponse)
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