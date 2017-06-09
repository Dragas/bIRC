package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

/**
 * Created by mgrid on 2017-05-27.
 */
open class IrcRoute(type: String, regexString: String, protected val callback: (Request) -> Response) : IrcRouteGroup(type, regexString)
{
    override fun attemptTrigger(request: Request): Response?
    {
        if (canTrigger(request))
            return callback(request)
        return null
    }
}