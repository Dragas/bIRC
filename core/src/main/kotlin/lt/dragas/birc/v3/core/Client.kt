package lt.dragas.birc.v3.core

import lt.dragas.birc.v3.core.io.Input
import lt.dragas.birc.v3.core.io.Output
import lt.dragas.birc.v3.core.route.RouteGroup
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


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
 * Such behavior can be wrapped in multithreading environment if necessary, as most of the time adapters
 * and router will not have any variable data in themselves.
 */
@Deprecated("Depends on another deprecated class and is a mess in general", ReplaceWith("Client()", "lt.dragas.birc.v3.core.main.Client"))
abstract class Client<Request, Response>(private val routes: Array<RouteGroup<Request, Response>>)
{
    protected val threadPoolSize = Runtime.getRuntime().availableProcessors()
    protected val socket = Socket()
    protected val executor: ExecutorService = Executors.newFixedThreadPool(threadPoolSize)
    protected abstract val sin: Input<Request>
    protected abstract val sout: Output<Response>

    abstract fun connect()

    abstract fun bindSocket()
    fun run()
    {
        connect()
        onConnect()
        process()
        onDisconnect()
    }

    open protected fun onConnect()
    {
        bindSocket()
    }

    open protected fun process()
    {
        for (i in 0..threadPoolSize - 1)
        {
            submitWork()
        }
        while (!socket.isClosed)
        {
            doWork()
        }

    }

    open protected fun submitWork()
    {
        executor.submit {
            doWork()
        }
    }

    open fun doWork()
    {
        val request = sin.getRequest()
        routes.forEach {
            it.attemptTrigger(request)?.apply {
                sout.writeResponse(this)
            }
        }
    }

    open protected fun onDisconnect()
    {

    }
}