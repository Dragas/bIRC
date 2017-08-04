package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer


/**
 * Router object, which handles all the [Route] objects that are used within the application.
 *
 * @param Request a wrapper object obtained from [Deserializer] implementation
 * @param Response a wrapper object returned from callback of corresponding route
 */
abstract class Router<Request, Response>
{
    protected open val routes: MutableList<Route<Request, Response>> = ArrayList()

    protected open val middlewares: MutableList<Middleware<Request, Response>> = ArrayList()
    /**
     * Returns a route builder, which handles assigning middlewares, callback and test callback
     */
    abstract fun builder(): RouteBuilder<Request, Response>

    /**
     * Adds a route to router, which is later used to test requests
     */
    open fun add(route: Route<Request, Response>)
    {
        if (!routes.contains(route))
            routes.add(route)
    }

    /**
     * a shorthand to build and add a route.
     */
    open fun add(route: RouteBuilder<Request, Response>)
    {
        add(route.build())
    }

    /**
     * Adds global middleware to router.
     */
    open fun add(middleware: Middleware<Request, Response>)
    {
        if (!middlewares.contains(middleware))
            middlewares.add(middleware)
    }

    /**
     * A shorthand to get middleware singleton from cache and add it as global middleware.
     */
    open fun add(title: String)
    {
        add(Middleware.getMiddleware(title) as Middleware<Request, Response>)
    }

    /**
     * Attempts consuming provided [request] request. Returns null on failure.
     *
     * Implementations should take into consideration, that there are global middlewares that should be tested against.
     */
    open fun consume(request: Request): Response?
    {
        if (middlewares.firstOrNull { !it.before(request) } != null)
            return null
        routes.forEach {
            val result = it.attemptTrigger(request)
            if (result != null && middlewares.firstOrNull { !it.after(result) } == null)
                return result
        }
        return null
    }
}