package lt.saltyjuice.dragas.chatty.v3.core.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Serializer
import java.io.OutputStream

/**
 * Contains only the most basic methods needed for [OutputStream] to function.
 *
 * Actual input stream is left for implementing classes to implement.
 */
abstract class Output<Response, OutputBlock>(protected val adapter: Serializer<Response, OutputBlock>)
{
    /**
     * Writes response to server.
     * @param response preformatted message that is sent to server.
     */
    abstract fun writeResponse(response: Response)
}