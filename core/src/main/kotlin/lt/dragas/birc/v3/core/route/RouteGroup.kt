package lt.dragas.birc.v3.core.route

abstract class RouteGroup<in T, R>(private val ignoreCaseForPrefix: Boolean, private val prefix: String, vararg private val routes: Route<T, R>)
{
    protected var isEnabled = true
    protected val regex = Regex(prefix)

    open fun attempTrigger(request: T): R?
    {
        if (canTrigger(request))
            routes.forEach {
                val response = it.attempTrigger(request)
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