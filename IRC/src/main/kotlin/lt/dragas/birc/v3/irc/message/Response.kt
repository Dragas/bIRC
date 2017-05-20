package lt.dragas.birc.v3.irc.message

/**
 * Response to server.
 *
 * Returned by local implementation of Route. Contains two constructors which easily let you build your response with either
 * direct target and message or straight up raw response.
 */
class Response : Message
{
    constructor(target: String, message: String)
    {
        this.target = target
        this.message = message
    }

    constructor(rawResponse: String)
    {
        this.rawMessage = rawResponse
    }
}