package lt.saltyjuice.dragas.chatty.v3.core.middleware

import lt.saltyjuice.dragas.chatty.v3.core.route.AfterResponse
import lt.saltyjuice.dragas.chatty.v3.core.route.BeforeRequest

object MiddlewareUtility
{

    @JvmStatic
    private val beforeMiddlewareChache: MutableMap<Class<BeforeMiddleware<*>>, BeforeMiddleware<*>> = mutableMapOf()
    @JvmStatic
    private val afterMiddlewareChache: MutableMap<Class<AfterMiddleware<*>>, AfterMiddleware<*>> = mutableMapOf()

    /**
     * Returns particular middleware by specified class.
     */
    @JvmStatic
    fun getBeforeMiddleware(clazz: Class<BeforeMiddleware<*>>): BeforeMiddleware<*>
    {
        val middleware = beforeMiddlewareChache[clazz] ?: clazz.newInstance()
        beforeMiddlewareChache[clazz] = middleware
        return middleware

    }

    /**
     * Returns particular middleware by specified class.
     */
    @JvmStatic
    fun getAfterMiddleware(clazz: Class<AfterMiddleware<*>>): AfterMiddleware<*>
    {
        val middleware = afterMiddlewareChache[clazz] ?: clazz.newInstance()
        afterMiddlewareChache[clazz] = middleware
        return middleware
    }

    @JvmStatic
    fun getBeforeMiddlewares(it: Any): List<Class<BeforeMiddleware<*>>>
    {
        val annotation = it.javaClass.getAnnotation(BeforeRequest::class.java)
        annotation ?: return listOf()
        return annotation.value.map()
        {
            it.java as Class<BeforeMiddleware<*>>
        }
    }

    @JvmStatic
    fun getAfterMiddlewares(it: Any): List<Class<AfterMiddleware<*>>>
    {
        val annotation = it.javaClass.getAnnotation(AfterResponse::class.java)
        annotation ?: return listOf()
        return annotation.value.map()
        {
            it.java as Class<AfterMiddleware<*>>
        }
    }
}