package lt.saltyjuice.dragas.chatty.v3.core.route


@Deprecated("Doesn't check for route type")
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