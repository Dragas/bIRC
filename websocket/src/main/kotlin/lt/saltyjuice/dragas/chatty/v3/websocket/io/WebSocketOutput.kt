package lt.saltyjuice.dragas.chatty.v3.websocket.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Output
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Response

/**
 * WebSocket implementation wrapper for regular Output
 *
 * @see Output
 */
interface WebSocketOutput : Output<Response, String>
{
}