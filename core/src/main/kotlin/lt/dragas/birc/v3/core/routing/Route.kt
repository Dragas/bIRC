package lt.dragas.birc.v3.core.routing

import java.util.regex.Pattern


/**
 * A simple route class that holds all abstraction related to Routing in general.
 *
 * Key idea here is that [Request] objects are matched with some sort of [pattern] and then a
 * [callback] is triggered, which is supposed to generate a non-nullable [Response] object. Since [callback]
 * is a lamba reference, you can implement MVC pattern (or model-response-controller) around
 * router objects.
 */
abstract class Route<in Request, out Response>(protected open val pattern: Pattern, open val callback: ((Request) -> Response)? = null)
{
    /**
     * Implementations should test [request] object with [pattern] provided while creating
     * this route object.
     *
     * @param request a request object that's supposed to be tested
     * @return true, when this route's [callback] can handle tested [request]
     */
    abstract fun canTrigger(request: Request): Boolean
}