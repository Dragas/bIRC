package lt.saltyjuice.dragas.chatty.v3.irc

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.controller.ChannelController
import lt.saltyjuice.dragas.chatty.v3.irc.controller.NicknameController
import lt.saltyjuice.dragas.chatty.v3.irc.controller.PingController
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
        NicknameController.initialize(router, settings)
        ChannelController.initialize(router)
        PingController.initialize(router)
    }

    override fun onConnect()
    {
        /*sin.getRequest()
        sin.getRequest()
        sout.writeResponse(Response("user", settings.user, settings.mode, settings.unused, settings.realname))
        sout.writeResponse(Response("nick", settings.nicknames[0]))*/
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