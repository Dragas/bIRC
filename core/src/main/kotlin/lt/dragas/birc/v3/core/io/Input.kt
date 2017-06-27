package lt.dragas.birc.v3.core.io

import lt.dragas.birc.v3.core.adapter.Deserializer
import java.io.InputStream
import java.util.*

/**
 * Contains default methods needed for [InputStream] to function.
 *
 * Since this is just a wrapper around socket's input stream, implementation should contain input related methods.
 *
 * @param inputStream a stream provided by socket that's connected to some server
 */
abstract class Input<Request>(inputStream: InputStream)
{
    protected val sin = Scanner(inputStream)
    protected abstract val adapter: Deserializer<Request>
    /**
     * Waits for next raw response from server. Mainly needed to "flush" unnecessary lines from server's output such as
     * MOTD, general notifications and other garbage or even to note one of the routes that next response is important.
     * For what ever reason.
     */
    open fun getRequest(): Request
    {
        val rawRequest = adapter.deserialize(sin.nextLine())
        return rawRequest
    }
}