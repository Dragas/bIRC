package lt.saltyjuice.dragas.chatty.v3.irc

import java.net.InetSocketAddress
import java.net.SocketAddress

/**
 * Handles settings for IRC client.
 */
open class IrcSettings
{
    open var address: String = ""
    open var port: Int = 6667
    open var user: String = "default"
    open var mode: String = "default"
    open var unused: String = "default"
    open var realname: String = ":default"
    open var nicknames: ArrayList<String> = ArrayList()
    open var channels: ArrayList<String> = ArrayList()

    open fun getSocketAddress(): SocketAddress
    {
        return InetSocketAddress(address, port)
    }
}