package lt.saltyjuice.dragas.chatty.v3.core.main

import lt.saltyjuice.dragas.chatty.v3.core.exception.InitializeAlreadyCalledException
import lt.saltyjuice.dragas.chatty.v3.core.exception.InitializeNotCalledException
import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.MiddlewareUtility
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import lt.saltyjuice.dragas.chatty.v3.core.route.Router
import lt.saltyjuice.dragas.chatty.v3.core.route.UsesControllers
import java.lang.Exception
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
abstract class Client<InputBlock, Request, Response, OutputBlock>
{
    /**
     * A wrapper for socket's input stream, which is used to deserialize provided data.
     */
    protected abstract val sin: Input<InputBlock, Request>
    /**
     * A wrapper for socket's output stream, which is used to serialize generated data by the bot.
     */
    protected abstract val sout: Output<Response, OutputBlock>
    /**
     * Handles testing of [Request] wrappers.
     */
    protected abstract val router: Router<Request, Response>

    /**
     * Input middleware container.
     *
     * Contains all the middlewares that each request must be tested against before they're used in the application.
     */
    protected open val beforeMiddlewares: MutableCollection<BeforeMiddleware<Request>> = mutableListOf()

    /**
     * Output middleware container.
     *
     * Contains all the middlewares that each response must be tested against when
     * they're supposed to be sent back to the server.
     */
    protected open val afterMiddlewares: MutableCollection<AfterMiddleware<Response>> = mutableListOf()


    protected open val controllers: MutableCollection<Controller<Request, Response>> = mutableListOf()

    private var initialized = false

    init
    {
        defaultIntance = this
    }

    /**
     * Implementations should handle how the client itself is initialized: for example routes,
     * client settings, thread pools, etc.
     *
     * Implementations must call super.initialize()
     */
    open fun initialize()
    {
        if (initialized)
            throw InitializeAlreadyCalledException()
        this.javaClass.annotations.forEach()
        {
            when (it)
            {
                is UsesControllers ->
                {
                    it.value.forEach {
                        val controller = it.java.constructors[0].newInstance()
                        try
                        {
                            controllers.add(controller as Controller<Request, Response>)
                            router.consume(controller)
                        }
                        catch (err: Exception)
                        {
                            throw Exception("for $controller", err)
                        }
                    }
                }
            }
        }
        MiddlewareUtility.getBeforeMiddlewares(this).forEach()
        {
            val middleware = MiddlewareUtility.getBeforeMiddleware(it) as BeforeMiddleware<Request>
            beforeMiddlewares.add(middleware)
        }
        MiddlewareUtility.getAfterMiddlewares(this).forEach()
        {
            val middleware = MiddlewareUtility.getAfterMiddleware(it) as AfterMiddleware<Response>
            afterMiddlewares.add(middleware)
        }
        initialized = true
    }

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
        if (!initialized)
            throw InitializeNotCalledException()
        val request = sin.getRequest()
        if (beforeMiddlewares.firstOrNull { !it.before(request) } != null) return
        val response = router.consume(request)
        response ?: return
        writeResponse(response)
    }

    open fun writeResponse(response: Response)
    {
        if (afterMiddlewares.firstOrNull { !it.after(response) } != null) return
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

    companion object
    {
        @JvmStatic
        private lateinit var defaultIntance: Client<*, *, *, *>

        @JvmStatic
        fun getInstance(): Client<*, *, *, *>
        {
            return defaultIntance
        }
    }
}