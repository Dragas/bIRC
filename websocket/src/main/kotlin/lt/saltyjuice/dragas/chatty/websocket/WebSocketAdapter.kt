package lt.saltyjuice.dragas.chatty.websocket

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Adapter
import lt.saltyjuice.dragas.chatty.websocket.message.Request
import lt.saltyjuice.dragas.chatty.websocket.message.Response
import javax.websocket.Session


/**
 * WebSocket message adapter.
 *
 * This adapter improves on [Adapter] in core by adding deserialize(String, Session) call, which in turn
 * calls the default deserialize(String) method.
 *
 * Implementations should have their own deserialization mechanism, be it GSON, Moshi, Jackson, etc. (Depending on format)
 * @see [Adapter]
 */
abstract class WebSocketAdapter : Adapter<String, Request, Response, String>()
{
    /**
     * Deserializes a request from string and then appends provided session object to it.
     */
    open fun deserialize(block: String, session: Session): Request
    {
        val request = deserialize(block)
        request.session
        return request
    }
}