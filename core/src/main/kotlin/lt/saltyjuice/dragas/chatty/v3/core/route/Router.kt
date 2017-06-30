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
    protected val routes: ArrayList<Route<Request, Response>> = ArrayList()

    /**
     * Returns a route builder, which handles assigning middlewares, callback and test callback
     */
    abstract fun builder(): RouteBuilder<Request, Response>

    /**
     * Adds a route to router, which is later to test
     */
    open fun add(route: Route<Request, Response>)
    {
        if (!routes.contains(route))
            routes.add(route)
    }

    /**
     * Attempts consuming provided [request] request. Returns null on failure.
     */
    open fun consume(request: Request): Response?
    {
        routes.forEach {
            val result = it.attemptTrigger(request)
            if (result != null)
                return result
        }
        return null
    }
}