package lt.saltyjuice.dragas.chatty.v3.core.middleware

import lt.saltyjuice.dragas.chatty.v3.core.route.After
import lt.saltyjuice.dragas.chatty.v3.core.route.Before
import java.lang.reflect.AnnotatedElement

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
    fun getBeforeMiddlewares(it: Any): List<Class<out BeforeMiddleware<*>>>
    {
        return getBeforeMiddlewares(it.javaClass)
    }

    @JvmStatic
    fun getAfterMiddlewares(it: Any): List<Class<out AfterMiddleware<*>>>
    {
        return getAfterMiddlewares(it.javaClass)
    }

    @JvmStatic
    fun getAfterMiddlewares(it: AnnotatedElement): List<Class<out AfterMiddleware<*>>>
    {
        val annotation = it.getAnnotation(After::class.java)
        annotation ?: return listOf()
        return annotation.value.map()
        {
            it.java
        }
    }

    @JvmStatic
    fun getBeforeMiddlewares(it: AnnotatedElement): List<Class<out BeforeMiddleware<*>>>
    {
        val annotation = it.getAnnotation(Before::class.java)
        annotation ?: return listOf()
        return annotation.value.map()
        {
            it.java
        }
    }
}