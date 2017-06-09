package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.core.route.Controller
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

/**
 * Created by mgrid on 2017-05-27.
 */
open class IrcRoute(regexString: String, private val callback: Controller<Request, Response>) : IrcRouteGroup(regexString)
{
    override fun attemptTrigger(request: Request): Response?
    {
        if (canTrigger(request))
            return callback.onTrigger(request)
        return null
    }
}