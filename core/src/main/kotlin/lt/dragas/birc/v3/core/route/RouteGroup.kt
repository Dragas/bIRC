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
    /**
     * IRC Specific
     * move this garbage to IRC implementation
     */
    companion object
    {
        /**
         * Default mode. Means that route does not work solely with private messages or channel messages.
         */
        @JvmField
        val NONE: Int = 0
        /**
         * Marks route as ping route. Shouldn't be used outside [Pong]
         */
        @JvmField
        val PING: Int = 4
        /**
         * Marks route as private message route.
         */
        @JvmField
        val PRIVATE: Int = 1
        /**
         * Marks route as channel route.
         */
        @JvmField
        val CHANNEL: Int = 2
    }
}