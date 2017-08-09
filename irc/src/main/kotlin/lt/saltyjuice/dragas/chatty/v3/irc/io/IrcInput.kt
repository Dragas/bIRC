package lt.saltyjuice.dragas.chatty.v3.irc.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import java.io.InputStream
import java.util.*

/**
 * An implementation of [Input] for IRC networks.
 */
open class IrcInput(override val adapter: IrcAdapter, inputStream: InputStream) : Input<String, Request>
{
    override val middlewares: MutableCollection<BeforeMiddleware<Request>> = mutableListOf()
    protected open val scanner = Scanner(inputStream)
    /**
     * Returns data for [getRequest] so that it could be used in deserializing the request.
     */
    open fun getData(): String
    {
        return scanner.nextLine()
    }

    override fun getRequest(): Request
    {
        return adapter.deserialize(getData())
    }

}