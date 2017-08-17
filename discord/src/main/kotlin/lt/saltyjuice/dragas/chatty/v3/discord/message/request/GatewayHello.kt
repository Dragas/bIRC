package lt.saltyjuice.dragas.chatty.v3.discord.message.request

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Hello

/**
 * Gateway Hello
 *
 * Sent on connection to the websocket. Defines the heartbeat interval that
 * the client should heartbeat to.
 */
open class GatewayHello() : OPRequest<Hello>()
{

}