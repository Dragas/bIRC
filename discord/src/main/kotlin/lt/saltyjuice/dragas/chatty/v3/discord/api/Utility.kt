package lt.saltyjuice.dragas.chatty.v3.discord.api

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.adapter.AuditLogChangeAdapter
import lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor.CommonInterceptor
import lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor.RateLimitInterceptor
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.AuditLogChange
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 */
object Utility
{
    /**
     * Equivalent to calling okHttpBuilder.build()
     *
     * Do note that this is the actual used value in the framework, thus
     * any changes made to previously mentioned builder will be moot and ignored
     * because of how lazy initialization works.
     *
     * Should you want to do any changes to this, call the builder version before doing anything.
     */
    @JvmStatic
    val okHttpClient: OkHttpClient by lazy()
    {
        okHttpBuilder.build()
    }
    /**
     * Equivalent to calling retrofitBuilder.build()
     *
     * Do note that this is the actual used value in the framework, thus
     * any changes made to previously mentioned builder will be moot and ignored
     * because of how lazy initialization works.
     *
     * Should you want to do any changes to this, call the builder version before doing anything.
     */
    @JvmStatic
    val retrofit: Retrofit by lazy()
    {
        retrofitBuilder.build()
    }
    /**
     * Equivalent to calling gsonBuilder.create()
     *
     * Do note that this is the actual used value in the framework, thus
     * any changes made to previously mentioned builder will be moot and ignored
     * because of how lazy initialization works.
     *
     * Should you want to do any changes to this, call the builder version before doing anything.
     */
    @JvmStatic
    val gson: Gson by lazy()
    {
        gsonBuilder.create()
    }
    /**
     * Equivalent to calling retrofit.create(DiscordAPI::class.java)
     *
     * Do note that this is the actual used value in the framework, thus
     * any changes made to previously mentioned builder will be moot and ignored
     * because of how lazy initialization works.
     *
     * Should you want to do any changes to this, call the builder version before doing anything.
     */
    @JvmStatic
    val discordAPI: DiscordAPI by lazy()
    {
        retrofit.create(DiscordAPI::class.java)
    }

    /**
     * Due to magic of OOP and Kotlin lazy initialization, you may call this method prior doing any [retrofit]
     * calls to add additional things to this [Retrofit] client, for example change the base URL or add a new converter factory
     * See [Retrofit.Builder] for what you can add.
     */
    @JvmStatic
    val retrofitBuilder: Retrofit.Builder by lazy()
    {
        Retrofit.Builder().apply()
        {
            baseUrl(Settings.BASE_URL)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create(gson))
            validateEagerly(true)
        }
    }

    /**
     * Due to magic of OOP and Kotlin lazy initialization, you may call this method prior doing any [retrofit] or [okHttpClient]
     * calls to add additional things to this [OkHttpClient], for example interceptors or cache or even a cookiejar.
     * See [OkHttpClient.Builder] for what you can add.
     */
    @JvmStatic
    val okHttpBuilder: OkHttpClient.Builder by lazy()
    {
        OkHttpClient.Builder().apply()
        {
            addInterceptor(CommonInterceptor())
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            addInterceptor(RateLimitInterceptor())
        }
    }

    /**
     * Due to magic of OOP and Kotlin lazy initialization, you may call this method prior doing any framework related
     * activities to add additional things to this [Gson] adapter, for example another type adapter or specify what fields are ignored.
     * See [GsonBuilder] for what you can add.
     */
    @JvmStatic
    val gsonBuilder: GsonBuilder by lazy()
    {
        GsonBuilder().apply()
        {
            registerTypeAdapter(AuditLogChange::class.java, AuditLogChangeAdapter())
            excludeFieldsWithoutExposeAnnotation()
            serializeNulls()
        }
    }
}