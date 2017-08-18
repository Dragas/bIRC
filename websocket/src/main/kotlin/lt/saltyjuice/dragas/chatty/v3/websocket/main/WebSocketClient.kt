package lt.saltyjuice.dragas.chatty.v3.websocket.main

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRouter
import org.glassfish.tyrus.client.ClientManager
import java.net.URI
import javax.websocket.ClientEndpointConfig
import javax.websocket.Endpoint

/**
 * WebSocket client.
 *
 * Core client implementation for WebSockets.
 *
 * Main difference here is that websockets are not clients, but instead servers with either one to many (server) or
 * one to one (client) connection, thus that yields several differences:
 *
 * * [connect] is equivalent to calling [ClientManager.connectToServer].
 * * [isConnected] always returns whether or not [connect] was called.
 * * There's a new method: [disconnect]. It should be called when client is supposed to be stopped.
 *
 * @see Client
 */
abstract class WebSocketClient<InputBlock, Request, Response, OutputBlock> : Client<InputBlock, Request, Response, OutputBlock>()
{
    /**
     * Client manager provided by tyrus that is used to connect to websocket based servers.
     */
    open val client: ClientManager = ClientManager.createClient()

    /**
     * Ensures that the implementations use [WebSocketRouter]
     */
    abstract override val router: WebSocketRouter<Request, Response>

    /**
     * Provides the URI that the client should connect to via WebSocket.
     */
    protected abstract val uri: URI

    /**
     * Default endpoint configuration.
     *
     * Implementations should override this so that they could provide their own encoders, decoders and other things.
     */
    protected open val cec: ClientEndpointConfig = ClientEndpointConfig.Builder.create().build()

    /**
     * Denotes whether or not the connection has been started.
     */
    protected open var isStarted = false


    override fun initialize()
    {

    }

    /**
     * Since websockets are technically servers, connect returns whether or not server managed to start successfully.
     */
    override fun connect(): Boolean
    {
        //val cec = ClientEndpointConfig.Builder.create().build()
        client.connectToServer(sin as Endpoint, cec, uri)
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
        //server.
        client.shutdown()
        isStarted = false
        onDisconnect()
    }

    /**
     * Performs actions that are supposed to be handled after the connection has ended.
     */

    override fun onDisconnect()
    {

    }
}
