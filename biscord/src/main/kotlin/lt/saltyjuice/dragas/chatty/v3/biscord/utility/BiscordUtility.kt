package lt.saltyjuice.dragas.chatty.v3.biscord.utility

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BiscordUtility
{
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
            //addInterceptor(HeaderInterceptor(Pair(Settings.MASHAPE_HEADER, Settings.MASHAPE_KEY)))
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
        builder.baseUrl("https://api.hearthstonejson.com/v1/latest/enUS/")
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
}