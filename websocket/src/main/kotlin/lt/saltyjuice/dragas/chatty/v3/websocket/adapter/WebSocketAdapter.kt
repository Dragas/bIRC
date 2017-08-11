package lt.saltyjuice.dragas.chatty.v3.websocket.adapter

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Adapter


/**
 * WebSocket message adapter.
 *
 * Implementations should have their own deserialization mechanism, be it GSON, Moshi, Jackson, etc. (Depending on format)
 * @see [Adapter]
 */
abstract class WebSocketAdapter<InputBlock, Request, Response, OutputBlock> : Adapter<InputBlock, Request, Response, OutputBlock>()
{

}