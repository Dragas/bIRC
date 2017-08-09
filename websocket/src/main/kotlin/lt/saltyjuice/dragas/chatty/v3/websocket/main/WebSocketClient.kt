package lt.saltyjuice.dragas.chatty.v3.websocket.main

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.core.route.Router
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketInput
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketOutput
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Request
import lt.saltyjuice.dragas.chatty.v3.websocket.message.Response
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
 *
 * @see Client
 */
abstract class WebSocketClient : Client<String, Request, Response, String>()
{
    abstract val server: Server //= Server(settings.hostName, settings.port, settings.rootPath, getEndpoints())
    override val router: Router<Request, Response>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val sin: WebSocketInput by lazy()
    {
        WebSocketEndpoint.getDefaultEndpoint()
    }
    override val sout: WebSocketOutput by lazy()
    {
        WebSocketEndpoint.getDefaultEndpoint()
    }
    protected open var isStarted = false
    /**
     * Since websockets are technically servers, connect returns whether or not server managed to start successfully.
     */
    override fun connect(): Boolean
    {
        server.start()
        isStarted = true
        onConnect()
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

    /**
     * Stops the server and calls [onDisconnect]
     */
    open fun disconnect()
    {
        server.stop()
        isStarted = false
        onDisconnect()
    }

    /**
     * Performs actions that are supposed to be handled after the connection has ended.
     */
    override fun onDisconnect()
    {
        WebSocketEndpoint.getDefaultEndpoint().onServerDestroyed()
    }
}