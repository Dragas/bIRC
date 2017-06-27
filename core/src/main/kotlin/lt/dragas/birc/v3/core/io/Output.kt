package lt.dragas.birc.v3.core.io

import lt.dragas.birc.v3.core.adapter.Serializer
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * Contains only the most basic methods needed for [OutputStream] to function.
 *
 * Since this is just a wrapper around socket's output stream, it should contain methods only
 * used for formatting output.
 * @param outputStream OutputStream : server socket's output stream.
 */
abstract class Output<Response>(outputStream: OutputStream)
{
    protected val sout: OutputStreamWriter = OutputStreamWriter(outputStream)
    protected abstract val adapter: Serializer<Response>
    /**
     * Writes response to server.
     * @param response preformatted message that is sent to server.
     */

    @Synchronized
    open fun writeResponse(response: String)
    {
        sout.write(response)
        sout.flush()
    }

    /**
     * Formats and sends response to server
     * @param response an object used by application to push around
     */
    @Synchronized
    open fun writeResponse(response: Response)
    {
        writeResponse(adapter.serialize(response))
    }
}