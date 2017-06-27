package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * A command route
 */
class CommandRoute(type: String, callback: (Request) -> Response) : IrcRoute(type, Pattern.compile(""), callback)
{
}