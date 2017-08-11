package lt.saltyjuice.dragas.chatty.v3.websocket.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import lt.saltyjuice.dragas.chatty.v3.core.io.Input

/**
 * WebSocket implementation wrapper for core input.
 *
 * @see Input
 */
interface WebSocketInput<Request> : Input<Any, Request>
{
    override val adapter: Deserializer<Any, Request> get() = throw NotImplementedError("Shouldn't be implmeneted, as this is handled by Decoding layer in tyrus.")
}