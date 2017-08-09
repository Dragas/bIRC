package lt.saltyjuice.dragas.chatty.v3.websocket.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Request

/**
 * WebSocket implementation wrapper for core input.
 *
 * @see Input
 */
interface WebSocketInput : Input<String, Request>
{

}