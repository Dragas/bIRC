package lt.saltyjuice.dragas.chatty.v3.irc.middleware

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response


/**
 * Wrapper for Core middleware
 */
abstract class IrcMiddleware : Middleware<Request, Response>()
{

}