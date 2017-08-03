package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.io.IrcOutput
import java.io.OutputStream

/**
 * Wrapper for Chatty/IRC output class.
 */
open class BIrcOutput(adapter: IrcAdapter, outputStream: OutputStream) : IrcOutput(adapter, outputStream)
{
    override fun writeResponse(response: String)
    {
        print(response)
        super.writeResponse(response)
    }
}