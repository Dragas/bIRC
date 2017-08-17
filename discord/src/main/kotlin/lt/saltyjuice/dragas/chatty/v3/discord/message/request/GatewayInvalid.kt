package lt.saltyjuice.dragas.chatty.v3.discord.message.request

/**
 * Gateway Invalid Session
 *
 * Sent to indicate one of at least three different situations: - the gateway could not initialize a session
 * after receiving an OP 2 Identify the gateway could not resume a previous session after receiving an
 * OP 6 Resume - the gateway has invalidated an active session and is requesting client action
 *
 * The inner d key is a boolean that indicates whether the session may be resumable.
 * See Connecting and Resuming for more information.
 */
open class GatewayInvalid : OPRequest<Boolean>()
{
}