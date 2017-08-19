package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware

/**
 * Sometimes routes have to contain logic that tests origin of request (for example host).
 * Middleware becomes an abstraction layer that separates such logic from routes and router itself.
 *
 * Since middlewares are singletons, they should not be registered more than once.
 */
abstract class Middleware<Request, Response>() : BeforeMiddleware<Request>, AfterMiddleware<Response>
{
    override fun before(request: Request): Boolean
    {
        return true
    }

    override fun after(response: Response): Boolean
    {
        return true
    }
}