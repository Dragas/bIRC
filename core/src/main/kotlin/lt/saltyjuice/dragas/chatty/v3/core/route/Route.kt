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
    var middlewares: List<Middleware<Request>> = listOf()
    var testCallback: (Request) -> Boolean = { false }
    var callback: (Request) -> Response? = { null }

    /**
     * Tests the request by first checking whether or not it passes the middleware test,
     * only after it does it test for actual patterns.
     */
    open fun canTrigger(request: Request): Boolean
    {
        middlewares.firstOrNull { it -> !it.handle(request) } ?: return testCallback(request)
        return false
    }

}