package lt.saltyjuice.dragas.chatty.v3.irc.message

import lt.saltyjuice.dragas.chatty.v3.irc.route.Command

/**
 * Response to server.
 *
 * Returned by local implementation of Route. Contains two constructors which easily let you build your response with either
 * direct target and message or straight up raw response.
 */
class Response(val command: String, vararg val arguments: String)
{
    constructor(command: Command, vararg arguments: String) : this(command.value, *arguments)

    val otherResponses = ArrayList<Response>()
}