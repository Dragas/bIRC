package lt.saltyjuice.dragas.chatty.v3.core.main

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output
import lt.saltyjuice.dragas.chatty.v3.core.routing.Router
import java.net.Socket

/**
 * An abstraction which defines how bot's client should be defined. Usually the pipeline
 * will be implemented as follows:
 * * [Client] connects to some server via [Socket]
 * * [Client] binds [Socket]'s input and output streams with [Input] and [Output] wrappers.
 * * [Client] requests some data from [Input], which handles wrapping data to something usable by the
 * bot.
 * * [Client] passes [Request] object to [Router], which handles testing [Route] objects against the provided Request
 * * [Client] passes [Response] object if it's returned by [Router] to [Output] wrapper, which handles
 * deserializing the Response
 * * [Client] then goes back to 3rd step unless the [Socket] is closed.
 *
 * Such behavior can be wrapped in multithreading environment, if necessary, as most of the time adapters
 * and router will not have any variable data in themselves and Input and Output streams have [Synchronized]
 * annotation on them.
 *
 * By default, [Client] does not have an internal socket, as some implementations won't be using it.
 * It's up to the implementing module to decide what will it use as a "Socket"
 */
abstract class Client<Request, Response>
{
    /**
     * A wrapper for socket's input stream, which is used to deserialize provided data.
     */
    protected abstract val sin: Input<Request>
    /**
     * A wrapper for socket's output stream, which is used to serialize generated data by the bot.
     */
    protected abstract val sout: Output<Response>
    /**
     * Handles testing of [Request] wrappers.
     */
    protected abstract val router: Router<Request, Response>

    /**
     * Implementations should handle how the client itself is initialized: for example routes,
     * client settings, thread pools, etc.
     */
    abstract fun initialize()

    /**
     * Implementations should handle how the client acts once socket has successfully connected
     */
    abstract fun onConnect()

    /**
     * Implementations should handle how the client acts once the socket has disconnected. Usually
     * it will just clean after itself: close any loggers it had, etc.
     */
    abstract fun onDisconnect()

    /**
     * Runs the pipeline, which is defined in [Client] comment.
     */
    open fun run()
    {
        val request = sin.getRequest()
        val response = router.consume(request)
        response ?: return
        sout.writeResponse(response)
    }

    /**
     * Implementations should handle how the client connects
     * @return true, if connection succeeds
     */
    abstract fun connect(): Boolean

    /**
     * Implementations should determine themselves on whether or not the client is still connected.
     * @return true, if the client is still connected
     */
    abstract fun isConnected(): Boolean
}