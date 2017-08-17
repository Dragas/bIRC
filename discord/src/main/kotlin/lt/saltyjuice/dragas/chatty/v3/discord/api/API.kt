package lt.saltyjuice.dragas.chatty.v3.discord.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor.CommonInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object API
{
    val okHttpClient: OkHttpClient by lazy()
    {
        OkHttpClient.Builder().apply()
        {
            addInterceptor(CommonInterceptor())
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()
    }

    val retrofit: Retrofit by lazy()
    {
        Retrofit.Builder().apply()
        {
            baseUrl(Settings.BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(gson))
            validateEagerly(true)
        }.build()
    }

    val gson: Gson by lazy()
    {
        GsonBuilder().apply()
        {
            serializeNulls()
        }.create()
    }

    val discordAPI: DiscordAPI by lazy()
    {
        retrofit.create(DiscordAPI::class.java)
    }
}