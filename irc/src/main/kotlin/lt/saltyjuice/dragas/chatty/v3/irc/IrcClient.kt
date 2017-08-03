package lt.saltyjuice.dragas.chatty.v3.irc

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.controller.ChannelController
import lt.saltyjuice.dragas.chatty.v3.irc.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.irc.io.IrcInput
import lt.saltyjuice.dragas.chatty.v3.irc.io.IrcOutput
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.middleware.AuthMiddleware
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter
import java.net.Socket

/**
 * IRC implementation of chatty client.
 */
open class IrcClient(protected open val settings: IrcSettings) : Client<String, Request, Response, String>()
{
    override val sin: IrcInput by lazy()
    {
        val input = IrcInput(adapter, socket.getInputStream())
        input
    }
    override val sout: IrcOutput by lazy()
    {
        val output = IrcOutput(adapter, socket.getOutputStream())
        output
    }
    override val router: IrcRouter = IrcRouter()
    protected open val adapter: IrcAdapter = IrcAdapter()
    protected open var socket: Socket = Socket()

    override fun initialize()
    {
        AuthMiddleware()
        ConnectionController.initialize(router, settings)
        ChannelController.initialize(router)
    }

    override fun onConnect()
    {

    }

    override fun onDisconnect()
    {

    }

    override fun connect(): Boolean
    {
        socket.connect(settings.getSocketAddress())
        return isConnected()
    }

    override fun isConnected(): Boolean
    {
        return socket.isConnected && !socket.isClosed
    }
}