package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware

/**
 * Sometimes routes have to contain logic that tests origin of request (for example host).
 * Middleware becomes an abstraction layer that separates such logic from routes and router itself.
 *
 * Since middlewares are singletons, they should not be registered more than once.
 */
abstract class Middleware<Request, Response>() : BeforeMiddleware<Request>, AfterMiddleware<Response>
{
    /**
     * Used for caching purposes. Also makes it so that you can get this middleware back by calling
     * [Middleware.getMiddleware] with that name.
     */
    abstract val name: String

    /**
     * Tests whether or not particular request passes the middleware.
     */
    @Deprecated("Use Before and After methods, instead. this calls before")
    open fun handle(request: Request): Boolean
    {
        return before(request)
    }

    override fun before(request: Request): Boolean
    {
        return true
    }

    override fun after(response: Response): Boolean
    {
        return true
    }

    init
    {
        registerMiddleware(this)
    }

    companion object
    {
        @JvmStatic
        private val middlewareCache = ArrayList<Middleware<*, *>>()

        @JvmStatic
        @Throws(Exception::class)
        fun getMiddleware(name: String): Middleware<*, *>
        {
            return middlewareCache.firstOrNull { name == it.name } ?: throw Exception("No such middleware")
        }

        @JvmStatic
        @Throws(Exception::class)
        fun registerMiddleware(middleware: Middleware<*, *>)
        {
            if (middlewareCache.firstOrNull { it.name == middleware.name } != null)
                throw Exception("Middleware under name ${middleware.name} is already registered")
            if (middlewareCache.contains(middleware))
                throw Exception("This particular middleware is already registered")
            middlewareCache.add(middleware)
        }
    }
}