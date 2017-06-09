package lt.dragas.birc.v3.core.route


abstract class Route<T, R>(regexString: String, protected val callback: (T) -> R) : RouteGroup<T, R>(regexString)
{

    override fun attemptTrigger(request: T): R?
    {
        if (canTrigger(request))
            return callback(request)
        return null
    }
    /*{
        if (isEnabled && type.and(request.type) == type && Regex(regexString).matches(request.message))
        {
            controller.onTrigger(request)
            return true
        }
        return false
    }*/


}