package lt.saltyjuice.dragas.chatty.v3.irc.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import java.io.InputStream

/**
 * An implementation of [Input] for IRC networks.
 */
class IrcInput(inputStream: InputStream, override val adapter: IrcAdapter) : Input<Request>(inputStream)
{

}