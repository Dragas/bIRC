package lt.saltyjuice.dragas.chatty.v3.core.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Serializer
import java.io.OutputStream

/**
 * Contains only the most basic methods needed for [OutputStream] to function.
 *
 * Actual input stream is left for implementing classes to implement.
 */
interface Output<Response, OutputBlock>
{
    /**
     * Used to serialize the response from application into something more transferable.
     */
    val adapter: Serializer<Response, OutputBlock>
    /**
     * Writes response to server.
     * @param response preformatted message that is sent to server.
     */
    fun writeResponse(response: Response)
}