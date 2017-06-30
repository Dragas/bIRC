package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.io.IrcInput
import java.io.InputStream

/**
 * Wrapper for Chatty/IRC input implementation
 */
class BIrcInput(adapter: IrcAdapter, inputStream: InputStream) : IrcInput(adapter, inputStream)
{
    override fun getData(): String
    {
        val line = super.getData()
        println(line)
        return line
    }
}