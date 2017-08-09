package lt.saltyjuice.dragas.chatty.v3.core.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Serializer
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import java.io.OutputStream

/**
 * Contains only the most basic methods needed for [OutputStream] to function.
 *
 * Actual input stream is left for implementing classes to implement.
 */
interface Output<Response, OutputBlock>
{
    /**
     * Output middleware container.
     *
     * Contains all the middlewares that each response must be tested against when
     * they're supposed to be sent back to the server.
     */
    val afterMiddlewares: MutableCollection<AfterMiddleware<Response>>
    /**
     * Used to serialize the response from application into something more transferable.
     */
    val adapter: Serializer<Response, OutputBlock>
    /**
     * Writes response to server after testing it against global middlewares provided in [middlewares]
     * @param response preformatted message that is sent to server.
     */
    fun writeResponse(response: Response)
}