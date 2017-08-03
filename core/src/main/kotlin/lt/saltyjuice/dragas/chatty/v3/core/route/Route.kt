package lt.saltyjuice.dragas.chatty.v3.core.route

/**
 * A simple route class that holds all abstraction related to Routing in general.
 *
 * Key idea here is that [Request] objects are matched by [testCallback] callback and then a
 * [callback] is triggered, which is supposed to generate a non-nullable [Response] object. Since [callback]
 * is a lambda reference, you can implement MVC pattern (or model-response-controller) around
 * route objects.
 */
abstract class Route<Request, Response>
{
    protected open var middlewares: List<Middleware<Request, Response>> = listOf()
    protected open var testCallback: (Request) -> Boolean = { false }
    protected open var callback: (Request) -> Response? = { null }

    /**
     * Tests the request by first checking whether or not it passes the middleware test,
     * only after it does it test for actual patterns.
     */
    open fun canTrigger(request: Request): Boolean
    {
        middlewares.firstOrNull { it -> !it.before(request) } ?: return testCallback(request)
        return false
    }

    open fun canRespond(response: Response): Boolean
    {
        val failingMiddleware = middlewares.firstOrNull { it -> !it.after(response) }
        return failingMiddleware == null
    }
    /**
     * Attempts consuming the provided request. On failure returns null.
     */
    open fun attemptTrigger(request: Request): Response?
    {
        if (canTrigger(request))
        {
            val response = callback(request) ?: return null
            if (canRespond(response))
                return response
        }
        return null
    }

}