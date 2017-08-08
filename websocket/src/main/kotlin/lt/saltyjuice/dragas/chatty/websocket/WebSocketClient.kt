package lt.saltyjuice.dragas.chatty.websocket

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output
import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.core.route.Router
import lt.saltyjuice.dragas.chatty.websocket.message.Request
import lt.saltyjuice.dragas.chatty.websocket.message.Response
import org.glassfish.tyrus.server.Server

/**
 * WebSocket client.
 *
 * Core client implementation for WebSockets.
 *
 * Main difference here is that websockets are not clients, but instead servers with either one to many (server) or
 * one to one (client) connection, thus that yields several differences:
 *
 * * [connect] is equivalent to starting the server.
 * * [isConnected] always returns whether or not [connect] was called.
 * * There's a new method: [disconnect]. It should be called when server is supposed to be stopped.
 */
abstract class WebSocketClient : Client<String, Request, Response, String>()
{
    abstract val server: Server //= Server(settings.hostName, settings.port, settings.rootPath, getEndpoints())
    override val router: Router<Request, Response>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val sin: Input<String, Request>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val sout: Output<Response, String>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    protected open var isStarted = false
    /**
     *
     */
    override fun connect(): Boolean
    {
        server.start()
        isStarted = true
        return true
    }

    /**
     * For servers, it barely matters if they're connected or not. Thus this instead returns whether or not
     * [connect] was called
     */
    override fun isConnected(): Boolean
    {
        return isStarted
    }

    override fun onConnect()
    {

    }

    open fun disconnect()
    {
        server.stop()
        isStarted = false
        onDisconnect()
    }

    override fun onDisconnect()
    {
        WebSocketEndpoint.defaultEndpoint.onServerDestroyed()
    }
}