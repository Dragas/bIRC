package lt.saltyjuice.dragas.chatty.v3.core.middleware

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware


/**
 * After middleware interface
 *
 * Indicates that implementation can be used in validating responses before they are sent to the server.
 *
 * @see Middleware
 */
interface AfterMiddleware<Response>
{
    /**
     * Returns true when [Response] can be sent to the server.
     */
    fun after(response: Response): Boolean
}