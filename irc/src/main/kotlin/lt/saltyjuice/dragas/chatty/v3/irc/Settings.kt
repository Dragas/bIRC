package lt.saltyjuice.dragas.chatty.v3.irc

import java.net.InetSocketAddress
import java.net.SocketAddress

/**
 * Handles settings for IRC client.
 */
open class Settings
{
    var address: String = ""
    var port: Int = 6667
    var user: String = "default"
    var mode: String = "default"
    var unused: String = "default"
    var realname: String = ":default"
    var nicknames: ArrayList<String> = ArrayList()
    var channels: ArrayList<String> = ArrayList()

    fun getSocketAddress(): SocketAddress
    {
        return InetSocketAddress(address, port)
    }
}