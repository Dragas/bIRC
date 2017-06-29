package lt.dragas.birc.v3.irc

import lt.dragas.birc.v3.core.io.Input
import lt.dragas.birc.v3.core.io.Output
import lt.dragas.birc.v3.core.main.Client
import lt.dragas.birc.v3.irc.adapter.IrcAdapter
import lt.dragas.birc.v3.irc.controller.ChannelController
import lt.dragas.birc.v3.irc.controller.NicknameController
import lt.dragas.birc.v3.irc.controller.PingController
import lt.dragas.birc.v3.irc.io.IrcInput
import lt.dragas.birc.v3.irc.io.IrcOutput
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.route.IrcRouter
import java.net.Socket

/**
 * IRC implementation of chatty client.
 */
open class IrcClient(protected val settings: Settings) : Client<Request, Response>()
{
    override val sin: Input<Request> by lazy()
    {
        val input = IrcInput(socket.getInputStream(), adapter)
        input
    }
    override val sout: Output<Response> by lazy()
    {
        val output = IrcOutput(socket.getOutputStream(), adapter)
        output
    }
    override val router: IrcRouter = IrcRouter()
    protected val adapter: IrcAdapter = IrcAdapter()
    protected var socket: Socket = Socket()


    override fun initialize()
    {
        NicknameController.initialize(router, settings)
        ChannelController.initialize(router)
        PingController.initialize(router)
    }

    override fun onConnect()
    {
        sin.getRequest()
        sin.getRequest()
        sout.writeResponse(Response("user", settings.user, settings.mode, settings.unused, settings.realname))
        sout.writeResponse(Response("nick", settings.nicknames[0]))
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