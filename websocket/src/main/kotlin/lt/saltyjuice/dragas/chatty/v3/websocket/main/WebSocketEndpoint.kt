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
import lt.saltyjuice.dragas.chatty.v3.websocket.exception.ServerDestroyedException
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketInput
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketOutput
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Request
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Response
import java.io.IOException
import javax.websocket.*


/**
 * Websocket endpoint.
 *
 * This is a programmatically implemented endpoint, which is meant to simplify websocket implementations
 */
open class WebSocketEndpoint(override val adapter: WebSocketAdapter) : Endpoint(), WebSocketInput, WebSocketOutput
{
    override val beforeMiddlewares: MutableCollection<BeforeMiddleware<Request>> = mutableListOf()
    override val afterMiddlewares: MutableCollection<AfterMiddleware<Response>> = mutableListOf()

    override fun getRequest(): Request = runBlocking<Request>
    {
        return@runBlocking requests.receive()
    }

    override fun writeResponse(response: Response)
    {
        launch(CommonPool)
        {
            responses.send(response)
        }
    }

    //protected open val sessions: MutableMap<Session, Job> = Collections.synchronizedMap(HashMap<Session, Job>())
    init
    {
        mDefaultEndpoint = this
    }

    open val requests: Channel<Request> = Channel(Channel.UNLIMITED)
    open val responses: Channel<Response> = Channel(Channel.UNLIMITED)
    protected open val job: Job = launch(CommonPool)
    {
        whileSelect()
        {
            responses.onReceive()
            {
                val rawResponse = adapter.serialize(it)
                it.session.asyncRemote.sendText(rawResponse)
                true
            }
        }
    }

    override fun onOpen(session: Session, config: EndpointConfig) = runBlocking<Unit>
    {
        session.addMessageHandler(MessageHandler.Whole<String> { message ->
            val request = adapter.deserialize(message, session)
            launch(CommonPool)
            {
                requests.send(request)
            }
        })
    }

    @Throws(IOException::class)
    override fun onClose(session: Session, reason: CloseReason)
    {
        println("Session id ${session.id} closed its connection. Reason: ${reason.closeCode} - ${reason.reasonPhrase}")
    }

    @OnError
    override fun onError(session: Session, throwable: Throwable)
    {
        System.err.println("Something happened for session id ${session.id}")
        throwable.printStackTrace()
        //System.err.println(throwable)
    }

    /**
     * This should be called once server's connection is stopped.
     */
    open fun onServerDestroyed()
    {
        job.cancel(ServerDestroyedException("That's all folks!"))
    }

    companion object
    {
        @JvmStatic
        private lateinit var mDefaultEndpoint: WebSocketEndpoint

        @JvmStatic
        fun getDefaultEndpoint(): WebSocketEndpoint
        {
            return mDefaultEndpoint
        }
    }
}