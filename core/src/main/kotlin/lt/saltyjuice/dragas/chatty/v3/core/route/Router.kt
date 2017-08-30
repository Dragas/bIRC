package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import lt.saltyjuice.dragas.chatty.v3.core.exception.RouteBuilderException
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import kotlin.streams.toList


/**
 * Router object, which handles all the [Route] objects that are used within the application.
 *
 * @param Request a wrapper object obtained from [Deserializer] implementation
 * @param Response a wrapper object returned from callback of corresponding route
 */
abstract class Router<Request, Response>
{
    protected open val routes: MutableList<Route<Request, Response>> = mutableListOf()
    /**
     * Returns a route builder, which handles assigning middlewares, callback and test callback and builds routes that can
     * be used by this router.
     */
    abstract fun builder(): Route.Builder<Request, Response>

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
    open fun add(route: Route.Builder<Request, Response>)
    {
        add(route.build())
    }

    open fun add(routes: List<Route.Builder<Request, Response>>)
    {
        routes.forEach(this::add)
    }

    /**
     * Attempts consuming provided [request] request. Returns null on failure.
     *
     * Implementations should take into consideration, that there are global middlewares that should be tested against.
     *
     * Your [Request] class MAY implement [Cloneable] interface, which permits your data being cloned, if it does, you may then
     * modify the request while testing in middlewares and test callbacks freely, without influencing other routes.
     *
     * @throws IllegalStateException when [initialize] wasn't called.
     * @return a list of all responses that were generated using [request]
     */
    @Throws(IllegalStateException::class)
    fun consume(request: Request): List<Response>
    {
        return routes.parallelStream()
                .flatMap()
                {
                    val cloned = if (request is Cloneable) request.javaClass.getMethod("clone").invoke(request) as Request else request
                    it.attemptTrigger(cloned)
                    it.getResponses().stream()
                }.toList()
    }

    /**
     * Scraps particular controller for methods that have [On] annotation and then builds routes for them.
     */
    @Throws(RouteBuilderException::class)
    @JvmOverloads
    fun consume(controller: Class<out Controller<Response>>, beforeMiddlewares: List<Class<out BeforeMiddleware<Request>>> = listOf(), afterMiddlewares: List<Class<out AfterMiddleware<Response>>> = listOf())
    {
        add(builder().consume(controller, beforeMiddlewares, afterMiddlewares))
    }
}