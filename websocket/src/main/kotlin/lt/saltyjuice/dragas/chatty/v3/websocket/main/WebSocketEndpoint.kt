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
 * Implementations should have [ClientEndpoint] annotation, which handles what that endpoint uses as encorder/decoder (adapter)
 * classes.
 */
abstract class WebSocketEndpoint<Request, Response> : Endpoint(), WebSocketInput<Request>, WebSocketOutput<Response>
{

    final override val adapter: WebSocketAdapter<Any, Request, Response, Any> get() = throw NotImplementedError("Shouldn't be implmeneted, as this is handled by Decoding/Encoding layer in tyrus.")
    protected open var session: Session? = null

    override val beforeMiddlewares: MutableCollection<BeforeMiddleware<Request>> = mutableListOf()
    override val afterMiddlewares: MutableCollection<AfterMiddleware<Response>> = mutableListOf()

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

    /**
     * Routes requests to [getRequest].
     */
    protected open var requests: Channel<Request>? = null
    /**
     * Routes responses from [writeResponse] to [responseListener]
     */
    protected open var responses: Channel<Response>? = null
    /**
     * Listens for responses that come through [writeResponse]
     */
    protected open var responseListener: Job? = null


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
    }

    @Throws(IOException::class)
    override fun onClose(session: Session, reason: CloseReason)
    {
        System.err.println("Session id ${session.id} closed its connection. Reason: ${reason.closeCode} - ${reason.reasonPhrase}")
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
}