package lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.discord.exception.RateLimitException
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class RateLimitInterceptor : Interceptor
{
    @Throws(RateLimitException::class)
    override fun intercept(chain: Interceptor.Chain): Response
    {
        val request = chain.request()
        val requestUrl = request.url().encodedPathSegments()
        val type = requestUrl[2]
        val identifier = requestUrl[3]
        val map = when (type)
        {
            GUILD ->
            {
                limitsPerGuild
            }
            CHANNELS ->
            {
                limitsPerChannel
            }
            else ->
            {
                globalLimits
            }
        }
        var limit = map[identifier]
        if (limit == null)
        {
            limit = Channel(Channel.UNLIMITED)
            map[identifier] = limit
        }
        else
        {
            val actualLimit = runBlocking<Limit> { limit!!.receive() }
            if (actualLimit.remaining == 0)
                waitForLimit(actualLimit)
        }

        val response = chain.proceed(request)
        val responseLimit = Limit(response)
        if (responseLimit.reset == null)
        {
            responseLimit.reset = 0
        }
        launch(CommonPool) { limit!!.send(responseLimit) }
        if (response.code() == 429)
        {
            return intercept(chain)
        }
        return response
    }

    private fun waitForLimit(limit: Limit)
    {
        if (shouldWait)
            limit.delayUntilReset()
        else
            throw RateLimitException()
    }

    companion object
    {
        @JvmStatic
        val X_RATELIMIT_GLOBAL = "X-RateLimit-Global"
        @JvmStatic
        val X_RATELIMIT_LIMIT = "X-RateLimit-Limit"
        @JvmStatic
        val X_RATELIMIT_REMAINING = "X-RateLimit-REMAINING"
        @JvmStatic
        val X_RATELIMIT_RESET = "X-RateLimit-RESET"

        @JvmStatic
        val CHANNELS = "channels"

        @JvmStatic
        val GUILD = "guilds"

        @JvmStatic
        private val limitsPerGuild: ConcurrentHashMap<String, Channel<Limit>> = ConcurrentHashMap()

        @JvmStatic
        private val limitsPerChannel: ConcurrentHashMap<String, Channel<Limit>> = ConcurrentHashMap()

        @JvmStatic
        private val globalLimits: ConcurrentHashMap<String, Channel<Limit>> = ConcurrentHashMap()


        /**
         * Sets whether or not the application should wait for rate limits to pass before sending a request.
         */
        @JvmStatic
        var shouldWait: Boolean = false
            @Synchronized
            get()
            {
                return field
            }
            @Synchronized
            set(value)
            {
                field = value
            }
    }

    private class Limit(response: Response)
    {
        val global: Boolean? = response.header(X_RATELIMIT_GLOBAL)?.toBoolean()
        val limit: Int? = response.header(X_RATELIMIT_LIMIT)?.toInt()
        val remaining: Int? = response.header(X_RATELIMIT_REMAINING)?.toInt()
        var reset: Long? = response.header(X_RATELIMIT_RESET)?.toLong()

        fun getDelay(): Long
        {
            val reset = this@Limit.reset ?: throw NullPointerException("Reset epoch was not specified")
            val time = Date().time / 1000 - reset
            return time
        }

        fun delayUntilReset() = runBlocking<Unit>()
        {
            val time = getDelay()
            if (time > 0)
                delay(time)
        }
    }
}