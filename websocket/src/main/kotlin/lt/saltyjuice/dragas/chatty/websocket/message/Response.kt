package lt.saltyjuice.dragas.chatty.websocket.message

import javax.websocket.Session

/**
 * Base class for websocket responses.
 *
 * Responses are supposed to be tied to their origin sessions so that they wouldn't be lost in time and
 * asynchronous environment.
 */
open class Response(val session: Session)
{
}