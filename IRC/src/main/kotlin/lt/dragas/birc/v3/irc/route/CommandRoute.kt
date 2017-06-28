package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import java.util.regex.Pattern

/**
 * A command route.
 *
 * Handles your usual commands like 422, 114. The difference here is that pattern argument is omitted,
 * since nearly always you won't need to act differently if there's no particular argument in response.
 *
 * @param type command this route responds to, usually defined in [IrcRouter]
 * @param callback a callback to invoke when this route triggers.
 */
class CommandRoute(type: String, callback: (Request) -> Response) : IrcRoute(type, Pattern.compile(""), callback)
{
}