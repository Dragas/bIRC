package lt.saltyjuice.dragas.chatty.v3.biscord

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BiscordUtility
{
    private val typingMap: MutableMap<String, Job> = mutableMapOf()
    val emptyCallback: Callback<Any> = object : Callback<Any>
    {
        override fun onFailure(call: Call<Any>, t: Throwable)
        {
            t.printStackTrace(System.err)
        }

        override fun onResponse(call: Call<Any>, response: Response<Any>)
        {
            println("response is successful: ${response.isSuccessful}")
        }
    }

    val gsonBuilder: GsonBuilder by lazy()
    {
        val builder = GsonBuilder()
        builder.apply {
            this.serializeNulls()
        }
        builder
    }

    val gson: Gson by lazy()
    {
        gsonBuilder.create()
    }

    val okHttpClientBuilder: OkHttpClient.Builder by lazy()
    {
        val builder = OkHttpClient.Builder()
        builder.apply {
            addInterceptor(HeaderInterceptor(Pair(Settings.MASHAPE_HEADER, Settings.MASHAPE_KEY)))
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        builder
    }

    val okHttpClient: OkHttpClient by lazy()
    {
        okHttpClientBuilder.build()
    }

    val retrofitBuilder: Retrofit.Builder by lazy()
    {
        val builder = Retrofit.Builder()
        builder.client(okHttpClient)
        builder.baseUrl("https://omgvamp-hearthstone-v1.p.mashape.com/")
        builder.addConverterFactory(GsonConverterFactory.create(gson))
        builder.validateEagerly(true)
        builder
    }

    val retrofit: Retrofit by lazy()
    {
        retrofitBuilder.build()
    }

    val API: CardAPI by lazy()
    {
        retrofit.create(CardAPI::class.java)
    }

    @JvmOverloads
    @JvmStatic
    fun keepTyping(channelId: String, callback: Callback<Any> = emptyCallback)
    {
        cancelTyping(channelId)
        typingMap[channelId] = launch(CommonPool)
        {
            while (true)
            {
                Utility.discordAPI.triggerTypingIndicator(channelId).enqueue(callback)
                delay(10000)
            }
        }
    }

    @JvmStatic
    fun cancelTyping(channelId: String)
    {
        typingMap.remove(channelId)?.cancel()
    }
}