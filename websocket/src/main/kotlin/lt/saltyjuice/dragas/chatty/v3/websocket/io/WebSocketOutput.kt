package lt.saltyjuice.dragas.chatty.v3.websocket.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Output

/**
 * WebSocket implementation wrapper for regular Output
 *
 * @see Output
 */
interface WebSocketOutput<Response, OutputBlock> : Output<Response, OutputBlock>
{
    //override val adapter: Serializer<Response, Any> get() = throw NotImplementedError("Shouldn't be implmeneted, as this is handled by Encoding layer in tyrus.")
}