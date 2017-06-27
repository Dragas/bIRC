package lt.dragas.birc.v3.core.routing

import lt.dragas.birc.v3.core.adapter.Deserializer

/**
 * Router object, which handles all the [Route] objects that are used within the application.
 *
 * @param Request a wrapper object obtained from [Deserializer] implementation
 * @param Response a wrapper object returned from callback of corresponding route
 */
abstract class Router<Request, Response>
{
    protected open val routes: ArrayList<Route<Request, Response>> = ArrayList()

    /**
     * Holds the default route when none of the testing routes correspond to request's pattern.
     */
    protected var defaultRoute: Route<Request, Response>? = null


    /**
     * Adds a route to the router.
     * @param route a wrapper object which holds [Request] testing logic
     */
    @Deprecated("Use when instead", ReplaceWith("`when`(route)"))
    open fun add(route: Route<Request, Response>)
    {
        `when`(route)
    }

    /**
     * Attempts consuming a [Request] wrapper object by testing all the internal routes against
     * the provided [request] object.
     * @param request [Request] object obtained from [Deserializer] implementation
     * @return a nullable [Response], if any of the internal routes succeeded in testing against
     * the provided [request] object and were capable of generating a response.
     */
    open fun consume(request: Request): Response?
    {
        var response: Response? = null

        routes.forEach { route ->
            if (route.canTrigger(request))
                response = route.callback?.invoke(request)
            if (response != null)
                return response
        }
        response = defaultRoute?.callback?.invoke(request)
        return response
    }

    /**
     * Appends a route to internal route list.
     * @param route a route object to append to internal list
     * @return this [Router], so that you could chain calls
     */
    fun `when`(route: Route<Request, Response>): Router<Request, Response>
    {
        if (!routes.contains(route))
            routes.add(route)
        return this
    }

    /**
     * Builds a route with provided pattern and callback, then appends it to internal route list.
     * @param pattern string regex pattern which is used to test [Request] objects.
     * @param callback a callback to invoke once [Request] object passes the test
     */
    fun `when`(pattern: String, callback: (Request) -> Response): Router<Request, Response>
    {
        return `when`(buildRoute(pattern, callback))
    }

    /**
     * Builds a default route with provided callback, which is used when none of the internal routes
     * pass the test.
     * @param callback default callback which is used to generate default response objects.
     */
    fun otherwise(callback: (Request) -> Response)
    {
        this.defaultRoute = buildRoute("", callback)
    }

    protected abstract fun buildRoute(pattern: String, callback: (Request) -> Response): Route<Request, Response>
}