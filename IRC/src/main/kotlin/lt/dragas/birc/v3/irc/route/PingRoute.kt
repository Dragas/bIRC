package lt.dragas.birc.v3.irc.route

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.route.IrcRouter.Companion.PING
import java.util.regex.Pattern

/**
 * Handles pings from IRC servers
 */
@Deprecated("Use command route instead", ReplaceWith("CommandRoute(PING, callback)"))
class PingRoute(callback: (Request) -> Response) : IrcRoute(PING, Pattern.compile(""), callback)
