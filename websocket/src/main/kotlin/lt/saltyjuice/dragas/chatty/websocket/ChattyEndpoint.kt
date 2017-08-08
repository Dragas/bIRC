package lt.saltyjuice.dragas.chatty.websocket

import java.io.IOException
import java.util.*
import javax.websocket.*


/**
 * Chatty endpoint.
 *
 * Implementing classes should note that they are server endpoint and application scoped.
 */
abstract class ChattyEndpoint
{
    protected val sessions: MutableSet<Session> = Collections.synchronizedSet(HashSet<Session>())
    @OnOpen
    open fun onOpen(session: Session)
    {
        sessions.add(session)
    }

    @OnMessage
    open fun onMessageReceive(message: String, session: Session)
    {

    }

    @OnClose
    @Throws(IOException::class)
    open fun onClose(session: Session, reason: CloseReason)
    {

    }

    @OnError
    open fun onError(session: Session, throwable: Throwable)
    {

    }
}