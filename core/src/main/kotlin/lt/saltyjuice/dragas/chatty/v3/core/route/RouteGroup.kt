package lt.saltyjuice.dragas.chatty.v3.core.route

@Deprecated("Parent should be a regular route instead")
abstract class RouteGroup<in T, R>(private val prefix: String, vararg protected val routes: RouteGroup<T, R>)
{
    /**
     * Denotes whether or not particular route is enabled.
     */
    protected var isEnabled = true
    protected val regex = Regex(prefix)

    open fun attemptTrigger(request: T): R?
    {
        if (canTrigger(request))
            routes.forEach {
                val response = it.attemptTrigger(request)
                if (response != null)
                    return response
            }
        return null
    }

    abstract fun canTrigger(request: T): Boolean

    /*{
        if (isEnabled && request.identifier().startsWith(prefix, ignoreCaseForPrefix))
        {
            request.message = request.message.replaceFirst(prefix, "", true)
            routes.forEach {
                if (it.canTrigger(request))
                {
                    return true
                }
            }
        }
        return false
    }*/
}