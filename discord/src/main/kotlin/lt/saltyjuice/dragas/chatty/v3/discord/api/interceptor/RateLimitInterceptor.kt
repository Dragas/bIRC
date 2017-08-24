package lt.saltyjuice.dragas.chatty.v3.discord.api.interceptor

import kotlinx.coroutines.experimental.*
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
        val map = when (requestUrl[0])
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
        val limit = map.remove(requestUrl[1])
        if (limit != null)
        {
            if (limit.limit == 0)
            {
                if (shouldWait)
                    limit.delayUntilReset()
                else
                    throw RateLimitException()
            }
        }
        val response = chain.proceed(request)
        if (response.code() != 429)
        {
            val newLimit = Limit(response)
            map[requestUrl[1]] = newLimit
            newLimit.queueRemoval()
        }
        return response
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
        private val limitsPerGuild: ConcurrentHashMap<String, Limit> = ConcurrentHashMap()

        @JvmStatic
        private val limitsPerChannel: ConcurrentHashMap<String, Limit> = ConcurrentHashMap()

        @JvmStatic
        private val globalLimits: ConcurrentHashMap<String, Limit> = ConcurrentHashMap()


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
        val reset: Long? = response.header(X_RATELIMIT_RESET)?.toLong()

        var removalJob: Job? = null

        fun getDelay(): Long
        {
            val reset = this@Limit.reset ?: throw NullPointerException("Reset epoch was not specified")
            val time = Date().time - reset
            return time
        }

        fun delayUntilReset() = runBlocking<Unit>()
        {
            val time = getDelay()
            if (time > 0)
                delay(time)
        }

        fun queueRemoval()
        {
            removalJob = launch(CommonPool)
            {
                val time = getDelay()
                if (time > 0)
                    delay(time)
                val map: ConcurrentHashMap<String, Limit> = when
                {
                    limitsPerChannel.containsValue(this@Limit) -> limitsPerChannel
                    limitsPerGuild.containsValue(this@Limit) -> limitsPerGuild
                    else -> globalLimits
                }
                for (entry in map.iterator())
                {
                    if (entry.value == this@Limit)
                    {
                        map.remove(entry.key)
                        break
                    }
                }
            }
        }
    }
}