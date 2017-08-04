package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.irc.IrcSettings


/**
 * Extends usual setting behavior with default settings as well as routes that
 * are created from json routes (ArrayList<CommandRoute>) field.
 */
open class BIrcSettings : IrcSettings()
{
    //var routes : ArrayList<CommandRoute> = ArrayList()
    /*override var address: String = "irc.purplesurge.com"
    override var port: Int = 6667
    override var user: String = "BIrc"
    override var mode: String = "0"
    override var unused: String = "*"
    override var realname: String = "Chatty/IRC implementation"
    override var nicknames: ArrayList<String> = arrayListOf("ne")
    override var channels: ArrayList<String> = arrayListOf("#nvrp")*/

    @Volatile
    open var currentMode = 0

    open var modes: HashMap<String, Int> = HashMap()

    fun getMode(mode: String): Int
    {
        return modes.getOrDefault(mode, -1)
    }
}