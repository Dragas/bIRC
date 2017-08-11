package lt.saltyjuice.dragas.chatty.v3.discord.message.response

/**
 * Gateway GatewayHeartbeat
 *
 * Used to maintain an active gateway connection. Must be sent every heartbeat_interval milliseconds after
 * the Hello payload is received. Note that this interval already has room for error,
 * and that client implementations do not need to send a heartbeat faster than what's specified.
 * The inner d key must be set to the last seq (s) received by the client.
 * If none has yet been received you should send null
 * (you cannot send a heartbeat before authenticating, however).
 */
class GatewayHeartbeat(override var data: Long) : OPResponse<Long>()
{
    override val op: Int = 1
}