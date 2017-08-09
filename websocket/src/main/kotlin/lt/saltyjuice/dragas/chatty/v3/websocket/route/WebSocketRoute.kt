package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Request
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Response


/**
 * WebSocketRoute wrapper for regular routes in chatty/core.
 *
 * @see Route
 */
abstract class WebSocketRoute : Route<Request, Response>()
{

}