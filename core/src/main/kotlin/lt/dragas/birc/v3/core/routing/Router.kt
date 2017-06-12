package lt.dragas.birc.v3.core.routing


/**
 * Router object, which handles all the [Route] objects that are used within the application.
 *
 *
 */
abstract class Router<Request, Response>
{
    protected open val routes: ArrayList<Route<Request, Response>> = ArrayList()


    open fun add(route: Route<Request, Response>)
    {
        if (!routes.contains(route))
            routes.add(route)
    }


    open fun consume(request: Request): Response?
    {
        var response: Response? = null

        routes.forEach { route ->
            if (route.canTrigger(request))
                response = route.callback?.invoke(request)
            if (response != null)
                return response
        }

        return response
    }


}