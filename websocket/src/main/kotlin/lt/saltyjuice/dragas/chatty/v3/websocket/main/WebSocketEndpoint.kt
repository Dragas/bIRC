package lt.saltyjuice.dragas.chatty.v3.websocket.main

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.whileSelect
import lt.saltyjuice.dragas.chatty.v3.websocket.adapter.WebSocketAdapter
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketInput
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketOutput
import java.io.IOException
import javax.websocket.*


/**
 * Websocket endpoint.
 *
 * This is a programmatically implemented endpoint, which is meant to simplify websocket implementations.
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
        val channel = requests ?: throw IllegalStateException("Either the connection hasn't started yet or is already closed.")
        return@runBlocking channel.receive()
    }

    override fun writeResponse(response: Response)
    {
        val channel = responses ?: throw IllegalStateException("Either the connection hasn't started yet or is already closed.")
        launch(CommonPool)
        {
            channel.send(response)
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
        session.addMessageHandler(baseClass, this::handleMessage)
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
        requests = null
        responses = null
    }

    @OnError
    override fun onError(session: Session, throwable: Throwable)
    {
        System.err.println("Something happened for session id ${session.id}")
        throwable.printStackTrace()
        //System.err.println(throwable)
    }

    /**
     * This method is called when a request from session is received. All requests coming through here are tested with
     * all available [beforeMiddlewares] before they are even passed to either [callbackMap] or application pipeline.
     * On success, the request is passed to test the [callbackMap] which looks for explicit handler for the particular
     * request. This way, the pipeline is filtered from request noise that might be ping requests, identifications, etc.
     * since they can be handled explicitly. Afterwards, [onMessage] is called, if and only if there is no
     * explicit handler for that particular call.
     */
    private fun handleMessage(request: Request)
    {
        onMessage(request)
        launch(CommonPool)
        {
            requests?.send(request)
        }
    }

    /**
     * Called when a message is received that does not have a particular handler registered for it. This means
     * that this message is somewhat general/generic and was passed to application pipeline. Implementations should not
     * try to handle the messages here (that's done by router->route->callback pipeline),
     * but instead check the request for key values that might be used in keeping the session alive.
     */
    abstract fun onMessage(request: Request)
}