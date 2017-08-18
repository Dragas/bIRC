package lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor

import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import okhttp3.Interceptor
import okhttp3.Response

class CommonInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Authorization", "Bot ${Settings.token}")
        builder.addHeader("User-Agent", "DiscordBot (${Settings.URL_LINK}, ${Settings.VERSION})")
        return chain.proceed(builder.build())
    }
}