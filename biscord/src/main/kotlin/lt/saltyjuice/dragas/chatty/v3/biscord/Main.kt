package lt.saltyjuice.dragas.chatty.v3.biscord

import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.biscord.controller.CardController
import lt.saltyjuice.dragas.chatty.v3.biscord.utility.BiscordUtility
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor.RateLimitInterceptor
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) = runBlocking<Unit>
{
    //Utility.okHttpBuilder.addInterceptor(HeaderInterceptor(Pair("X-Requested-With", "XMLHttpRequest")))
    BiscordUtility.okHttpClientBuilder.connectTimeout(0, TimeUnit.MILLISECONDS)
    RateLimitInterceptor.shouldWait = true
    CardController.initialize()
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

inline fun Boolean.doIf(predicate: () -> Unit): Boolean
{
    if (this)
        predicate.invoke()
    return this
}