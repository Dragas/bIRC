package lt.saltyjuice.dragas.chatty.websocket.message

import javax.websocket.Session

/**
 * Request for Websocket implementations.
 *
 * Deserialized requests are tied to their sessions so that their origin wouldn't be lost in asynchronous environment
 * that websocket implementations are supposed to be.
 *
 * Implementations should add their own fields here
 */
open class Request()
{
    lateinit open var session: Session

    constructor(session: Session) : this()
    {
        this.session = session
    }
}