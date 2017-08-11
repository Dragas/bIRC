package lt.saltyjuice.dragas.chatty.v3.websocket.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Serializer
import lt.saltyjuice.dragas.chatty.v3.core.io.Output

/**
 * WebSocket implementation wrapper for regular Output
 *
 * Since websockets already handle decoding and encoding, this class just becomes a compatability
 * layer between implementing router and the websocket.
 * @see Output
 */
interface WebSocketOutput<Response> : Output<Response, Any>
{
    override val adapter: Serializer<Response, Any> get() = throw NotImplementedError("Shouldn't be implmeneted, as this is handled by Encoding layer in tyrus.")
}