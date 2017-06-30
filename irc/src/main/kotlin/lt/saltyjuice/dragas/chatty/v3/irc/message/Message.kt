package lt.saltyjuice.dragas.chatty.v3.irc.message

/**
 * A super class for IRC server message syntax.
 */
@Deprecated("Redundant. Request messages and response messages barely have anything in common")
open class Message
{
    open var rawMessage: String = ""
    open var target: String = ""
    open var username: String = ""
    open var message: String = ""
}