package lt.saltyjuice.dragas.chatty.v3.websocket.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Route


/**
 * WebSocketRoute wrapper for regular routes in chatty/core.
 *
 * @see Route
 */
abstract class WebSocketRoute<Request, Response> : Route<Request, Response>()
{

}