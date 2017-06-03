package lt.dragas.birc.v3.irc.message

/**
 * A super class for IRC server message syntax.
 */
open class Message
{
    open var rawMessage: String = ""
    open var target: String = ""
    open var username: String = ""
    open var message: String = ""
}