package lt.saltyjuice.dragas.chatty.v3.biscord.utility

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds provided headers to all requests emitted by OKHttp client with this interceptor
 */
open class HeaderInterceptor(vararg private val headers: Pair<String, String>) : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        val request = chain.request().newBuilder()
        headers.forEach()
        { (key, value) ->
            request.addHeader(key, value)
        }
        return chain.proceed(request.build())
    }
}