package lt.dragas.birc.v3.irc.model

/**
 * Represents channel object.
 */
open class Channel
{
    var topic: Topic = Topic()
    var name = ""
    val users = ArrayList<User>()
}