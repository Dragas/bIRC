package lt.saltyjuice.dragas.chatty.v3.irc.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Output
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import java.io.OutputStream
import java.io.OutputStreamWriter

/**
 * An implementation of [Output] meant for IRCs
 */
open class IrcOutput(adapter: IrcAdapter, outputStream: OutputStream) : Output<Response, String>(adapter)
{
    protected val writer = OutputStreamWriter(outputStream)

    override fun writeResponse(response: Response)
    {
        writeResponse(adapter.serialize(response))
    }

    open fun writeResponse(response: String)
    {
        writer.write(response)
        writer.flush()
    }
}