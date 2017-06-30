package lt.saltyjuice.dragas.chatty.v3.irc.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Output
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import java.io.OutputStream

/**
 * An implementation of [Output] meant for IRCs
 */
class IrcOutput(outputStream: OutputStream, override val adapter: IrcAdapter) : Output<Response>(outputStream)
{

}