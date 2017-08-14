package lt.saltyjuice.dragas.chatty.v3.websocket.main

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.whileSelect
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.websocket.adapter.WebSocketAdapter
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketInput
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketOutput
import java.io.IOException
import javax.websocket.*


/**
 * Websocket endpoint.
 *
 * This is a programmatically implemented endpoint, which is meant to simplify websocket implementations.
 *
 * Due to how Tyrus works, you may only add one handler per media type (be it text or byte buffer). Chatty/WebSocket Endpoint
 * implementation works around this by providing its own [addMessageHandler] method, which works much like Tyrus implementation
 * (permits adding only one callback per type) but extends on the fact that everything is routed through [onMessage], therefore requiring
 * that those multiple types would have some sort of base class, which is provided in [baseClass].
 */
abstract class WebSocketEndpoint<InputBlock, Request, Response, OutputBlock> : Endpoint(), WebSocketInput<InputBlock, Request>, WebSocketOutput<Response, OutputBlock>
{

    /**
     * An adapter for this endpoint instance.
     *
     * Due to how tyrus works and how clients are implemented, this should be a
     * lazily initialized instance from adapter singleton.
     *
     * For example in client
     * ```kotlin
     *    override val cec: ClientEndpointConfig = ClientEndpointConfig.Builder.create()
     *        .apply()
     *        {
     *            decoders(listOf(DiscordAdapter::class.java))
     *            encoders(listOf(DiscordAdapter::class.java))
     *        }
     *        .build()
     * ```
     *
     *  And then here
     *  ```kotlin
     *     override abstract val adapter: DiscordAdapter by lazy()
     *     {
     *         DiscordAdapter.defaultInstance
     *     }
     *  ```
     */
    override abstract val adapter: WebSocketAdapter<InputBlock, Request, Response, OutputBlock>
    /**
     * Holds reference to this endpoints' session. This only matters if you intend on sending messages outside
     * Chatty/Core lifecycle, for example ping sort of messages.
     */
    protected open var session: Session? = null
    /**
     * Declares base class for all requests that will be incoming through this endpoint implementation.
     */
    protected abstract val baseClass: Class<Request>
    /**
     * Implements global middlewares that test requests before they are sent to the lifecycle pipeline.
     */
    override val beforeMiddlewares: MutableCollection<BeforeMiddleware<Request>> = mutableListOf()
    /**
     * Implements global middlewares that test responses before they are sent to the great beyond.
     */
    override val afterMiddlewares: MutableCollection<AfterMiddleware<Response>> = mutableListOf()

    /**
     * Holds references to all callbacks for this endpoint. This shouldn't be called directly under any circumstances.
     */
    private val callbackMap: MutableList<WebSocketCallback<*, *>> = mutableListOf()

    private var initialized = false

    /**
     * Routes requests to [getRequest].
     */
    private var requests: Channel<Request>? = null
    /**
     * Routes responses from [writeResponse] to [responseListener]
     */
    private var responses: Channel<Response>? = null
    /**
     * Listens for responses that come through [writeResponse]
     */
    private var responseListener: Job? = null


    override fun getRequest(): Request = runBlocking<Request>
    {
        return@runBlocking requests!!.receive()
    }

    override fun writeResponse(response: Response)
    {
        launch(CommonPool)
        {
            if (afterMiddlewares.firstOrNull { !it.after(response) } == null)
                responses!!.send(response)
        }
    }


    override fun onOpen(session: Session, config: EndpointConfig)
    {
        requests = Channel(Channel.UNLIMITED)
        responses = Channel(Channel.UNLIMITED)
        this@WebSocketEndpoint.session = session
        responseListener = launch(CommonPool)
        {
            whileSelect()
            {
                responses?.onReceive()
                { response ->

                    session.asyncRemote.sendObject(response)
                    true
                }
            }
        }
        session.addMessageHandler(baseClass, this::onMessage)
        initialized = true
    }

    @Throws(IOException::class)
    override fun onClose(session: Session, reason: CloseReason)
    {
        System.err.println("Session id ${session.id} closed its connection. Reason: ${reason.closeCode.code} - ${reason.reasonPhrase}")
        responseListener?.cancel()
        responseListener = null
        requests?.close()
        responses?.close()
    }

    @OnError
    override fun onError(session: Session, throwable: Throwable)
    {
        System.err.println("Something happened for session id ${session.id}")
        throwable.printStackTrace()
        //System.err.println(throwable)
    }

    /**
     * This method is called when a request from session is received. Implementations should first call the superclass implementation
     * which checks if the request in question is one of the kind and should be handled regardless.
     */
    open fun onMessage(request: Request)
    {
        if (beforeMiddlewares.firstOrNull { !it.before(request) } != null)
            return
        val callable = callbackMap.firstOrNull { it.canBeCalled(request as Any) } as? WebSocketCallback<Request, Any>
                ?: callbackMap.firstOrNull { it.canBeCalledPartially(request as Any) } as? WebSocketCallback<Request, Any>
        if (callable == null)
        {
            println("$this W: Unhandled callback for $request")
            return
        }
        callable.call(request)
    }


    @Synchronized
    fun <T> addMessageHandler(clazz: Class<T>, callback: ((T) -> Unit))
    {
        if (callbackMap.indexOfFirst { it.canBeCalled(clazz) } != -1)
        {
            throw IllegalArgumentException("There's already a callback for ${clazz.name}")
        }
        if (initialized)
        {
            throw IllegalStateException("This endpoint is already initialized and it shouldn't be modified any further.")
        }
        callbackMap.add(WebSocketCallback(clazz, callback))
    }

}