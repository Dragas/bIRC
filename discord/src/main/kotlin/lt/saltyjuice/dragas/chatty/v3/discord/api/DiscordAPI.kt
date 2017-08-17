package lt.saltyjuice.dragas.chatty.v3.discord.api

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit
import retrofit2.Call
import retrofit2.http.GET

interface DiscordAPI
{
    @GET("gateway/bot")
    fun gatewayInit(): Call<GatewayInit>
}