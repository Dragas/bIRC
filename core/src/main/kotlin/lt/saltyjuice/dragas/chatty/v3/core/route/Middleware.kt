package lt.saltyjuice.dragas.chatty.v3.core.route

/**
 * Sometimes routes have to contain logic that tests origin of request (for example host).
 * Middleware becomes an abstraction layer that separates such logic from routes.
 *
 * Since middlewares are singletons, they should not be registered more than once.
 */
abstract class Middleware<Request>()
{
    /**
     * Used for caching purposes. Also makes it so that you can get this middleware back by calling
     * [Middleware.getMiddleware] with that name.
     */
    abstract val name: String

    /**
     * Tests whether or not particular request passes the middleware.
     */
    abstract fun handle(request: Request): Boolean

    init
    {
        registerMiddleware(this)
    }

    companion object
    {
        @JvmStatic
        private val middlewareCache = HashMap<String, Middleware<*>>()

        @JvmStatic
        fun getMiddleware(name: String): Middleware<*>
        {
            return middlewareCache[name] ?: throw Exception("No such middleware")
        }

        @JvmStatic
        fun registerMiddleware(middleware: Middleware<*>)
        {
            if (middlewareCache.containsKey(middleware.name))
                throw Exception("Middleware under name ${middleware.name} is already registered")
            if (middlewareCache.containsValue(middleware))
                throw Exception("This particular middleware is already registered")
            middlewareCache[middleware.name] = middleware
        }
    }
}