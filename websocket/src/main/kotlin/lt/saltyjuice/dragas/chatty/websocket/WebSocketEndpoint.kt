package lt.saltyjuice.dragas.chatty.websocket

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import kotlinx.coroutines.experimental.selects.whileSelect
import lt.saltyjuice.dragas.chatty.websocket.exception.ServerDestroyedException
import lt.saltyjuice.dragas.chatty.websocket.message.Request
import lt.saltyjuice.dragas.chatty.websocket.message.Response
import java.io.IOException
import javax.websocket.*


/**
 * Websocket endpoint.
 *
 * This is a programmatically implemented endpoint, which is meant to simplify websocket implementations
 */
open class WebSocketEndpoint(protected val adapter: WebSocketAdapter) : Endpoint()
{
    //protected open val sessions: MutableMap<Session, Job> = Collections.synchronizedMap(HashMap<Session, Job>())
    init
    {
        defaultEndpoint = this
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
        lateinit var defaultEndpoint: WebSocketEndpoint
    }
}