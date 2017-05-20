package lt.dragas.birc.v3.irc.message

/**
 * A super class for IRC server message syntax.
 */
open class Message
{
    var rawMessage: String = ""
    var target: String = ""
    var username: String = ""
    var message: String = ""
}