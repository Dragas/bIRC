package lt.saltyjuice.dragas.chatty.v3.discord.message.request

/**
 * Gateway Heartbeat ACK
 *
 * Used for the client to maintain an active gateway connection.
 * Sent by the server after receiving a Gateway Heartbeat
 */
open class GatewayAck : OPRequest<Any>()
{
}