package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.MiddlewareUtility
import java.lang.reflect.Method


/**
 * Router object, which handles all the [Route] objects that are used within the application.
 *
 * @param Request a wrapper object obtained from [Deserializer] implementation
 * @param Response a wrapper object returned from callback of corresponding route
 */
abstract class Router<Request, Response>
{
    protected open val routes: MutableList<Route<Request, Response>> = ArrayList()
    /**
     * Returns a route builder, which handles assigning middlewares, callback and test callback
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


    /**
     * Attempts consuming provided [request] request. Returns null on failure.
     *
     * Implementations should take into consideration, that there are global middlewares that should be tested against.
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

    /**
     * Scraps particular controller for methods that have [On] annotation and then builds routes for them.
     */
    fun consume(controller: Controller<Request, Response>)
    {
        controller.methods().forEach()
        { it ->
            val builder = builder()
            consumeMethod(builder, controller, it)
            add(builder)
        }
    }

    /**
     * Should any additional annotations be added later in core or protocol implementations, they should be scrapped here.
     * Calling super is mandatory.
     */
    open fun consumeMethod(builder: Route.Builder<Request, Response>, controller: Controller<Request, Response>, method: Method)
    {
        val annotations = method.annotations
        annotations.forEach()
        {
            when (it)
            {
                is When ->
                {
                    val testCallbackAnnotation = method.getAnnotation(When::class.java)
                    val testCallback = controller.javaClass.methods.find { it.name == testCallbackAnnotation?.value }
                    val type = method.getAnnotation(On::class.java).clazz
                    if (testCallbackAnnotation != null)
                    {
                        testCallback ?: throw NoSuchMethodException("Unable to find method named $testCallbackAnnotation")
                        builder.testCallback()
                        {
                            it as Any
                            type.java.isAssignableFrom(it.javaClass) && testCallback.invoke(controller, it) as Boolean
                        }
                    }
                    else
                    {
                        builder.testCallback()
                        {
                            it as Any
                            type.java.isAssignableFrom(it.javaClass)
                        }
                    }
                }
                is Before ->
                {
                    it.value.forEach()
                    { clazz ->
                        builder.before(clazz.java as Class<BeforeMiddleware<Request>>)
                    }

                }
                is After ->
                {
                    it.value.forEach()
                    { clazz ->
                        builder.after(clazz.java as Class<AfterMiddleware<Response>>)
                    }
                }
                is Description ->
                {
                    builder.description(it.value)
                }
            }
        }
        if (method.getAnnotation(Description::class.java) == null)
            builder.description("${controller.javaClass.canonicalName}#${method.name}")

        builder.callback()
        { request ->
            method.invoke(controller, request) as Response?
        }

        MiddlewareUtility.getBeforeMiddlewares(controller).forEach()
        {
            builder.before(it as Class<BeforeMiddleware<Request>>)
        }
        MiddlewareUtility.getAfterMiddlewares(controller).forEach()
        {
            builder.after(it as Class<AfterMiddleware<Response>>)
        }
    }
}