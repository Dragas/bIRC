package lt.dragas.birc.v3.core.route


abstract class Route<T, R>(ignoreCase: Boolean, regexString: String, protected val controller: Controller<T, R>) : RouteGroup<T, R>(ignoreCase, regexString)
{

    override fun attempTrigger(request: T): R?
    {
        if (canTrigger(request))
            return controller.onTrigger(request)
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