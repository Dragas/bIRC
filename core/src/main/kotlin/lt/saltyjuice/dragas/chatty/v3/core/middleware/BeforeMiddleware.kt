package lt.saltyjuice.dragas.chatty.v3.core.middleware

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware

/**
 * Before middleware interface.
 *
 * Indicates that implementation can be used as a middleware in validating requests.
 *
 * @see Middleware
 */
interface BeforeMiddleware<Request>
{
    /**
     * Returns true when [Request] may be used in application.
     */
    fun before(request: Request): Boolean
}