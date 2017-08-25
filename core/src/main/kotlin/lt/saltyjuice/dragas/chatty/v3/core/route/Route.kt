package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.exception.DuplicateMiddlewareException
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.MiddlewareUtility

/**
 * A simple route class that holds all abstraction related to Routing in general.
 *
 * Key idea here is that [Request] objects are matched by [testCallback] callback and then a
 * [callback] is triggered, which is supposed to generate a non-nullable [Response] object. Since [callback]
 * is a lambda reference, you can implement MVC pattern (or model-response-controller) around
 * route objects.
 */
open class Route<Request, Response>
{
    protected open val beforeMiddlewares: MutableList<BeforeMiddleware<Request>> = mutableListOf()
    protected open val afterMiddlewares: MutableList<AfterMiddleware<Response>> = mutableListOf()
    protected open var testCallback: (Request) -> Boolean = { true }
    protected open var callback: (Request) -> Response? = { null }
    protected open var description: String = ""

    /**
     * Tests the request by first checking whether or not it passes the middleware test,
     * only after it does it test for actual patterns.
     */
    open fun canTrigger(request: Request): Boolean
    {
        return beforeMiddlewares.firstOrNull { it -> !it.before(request) } == null && testCallback(request)
    }

    /**
     * Tests whether or not particular response can be sent back to the server.
     */
    open fun canRespond(response: Response): Boolean
    {
        return afterMiddlewares.firstOrNull { it -> !it.after(response) } == null
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

    /**
     * The base route builder for all Chatty implementations.
     *
     * Contains several very basic methods that help you build routes for [Router]
     */
    abstract class Builder<Request, Response>
    {
        protected open val mBeforeMiddlewares: MutableList<BeforeMiddleware<Request>> = mutableListOf()
        protected open val mAfterMiddlewares: MutableList<AfterMiddleware<Response>> = mutableListOf()
        protected open var mCallback: ((Request) -> Response?) = { null }
        protected open var mTestCallback: ((Request) -> Boolean) = { false }
        protected open var mDescription: String = ""

        open fun before(clazz: Class<BeforeMiddleware<Request>>): Builder<Request, Response>
        {
            val middleware = MiddlewareUtility.getBeforeMiddleware(clazz as Class<BeforeMiddleware<*>>)
            if (mBeforeMiddlewares.contains(middleware))
                throw DuplicateMiddlewareException("Particular middleware is already declared for this callback: $clazz")
            mBeforeMiddlewares.add(middleware as BeforeMiddleware<Request>)
            return this
        }

        open fun after(clazz: Class<AfterMiddleware<Response>>): Builder<Request, Response>
        {
            val middleware = MiddlewareUtility.getAfterMiddleware(clazz as Class<AfterMiddleware<*>>)
            if (mAfterMiddlewares.contains(middleware))
                throw DuplicateMiddlewareException("Particular middleware is already declared for this callback: $clazz")
            mAfterMiddlewares.add(middleware as AfterMiddleware<Response>)
            return this
        }

        open fun callback(callback: (Request) -> Response?): Builder<Request, Response>
        {
            this.mCallback = callback
            return this
        }

        open fun testCallback(callback: (Request) -> Boolean): Builder<Request, Response>
        {
            this.mTestCallback = callback
            return this
        }

        open fun description(string: String): Builder<Request, Response>
        {
            this.mDescription = string
            return this
        }


        fun build(): Route<Request, Response>
        {
            return adapt(returnableRoute())
        }

        /**
         * Implementations should return a raw route object which is later used in [adapt] to add all the callbacks, middlewares, etc.
         */
        abstract fun returnableRoute(): Route<Request, Response>

        /**
         * Copies all fields from this [Builder] to provided route. Anyone overriding this method MUST call super.adapt(route)
         */
        open fun adapt(route: Route<Request, Response>): Route<Request, Response>
        {
            route.beforeMiddlewares.addAll(mBeforeMiddlewares)
            route.afterMiddlewares.addAll(mAfterMiddlewares)
            route.callback = mCallback
            route.testCallback = mTestCallback
            route.description = mDescription
            return route
        }
    }
}