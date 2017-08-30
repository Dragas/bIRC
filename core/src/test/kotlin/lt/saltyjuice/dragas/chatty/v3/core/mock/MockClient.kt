package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.core.route.After
import lt.saltyjuice.dragas.chatty.v3.core.route.Before
import lt.saltyjuice.dragas.chatty.v3.core.route.UsesControllers


@Before(MockBeforeMiddleware::class)
@After(MockAfterMiddleware::class)
@UsesControllers(MockController::class)
class MockClient : Client<String, MockRequest, MockResponse, String>()
{
    /**
     * A wrapper for socket's input stream, which is used to deserialize provided data.
     */
    override val sin: MockEndpoint by lazy()
    {
        MockEndpoint.instance
    }

    /**
     * A wrapper for socket's output stream, which is used to serialize generated data by the bot.
     */
    override val sout: MockEndpoint by lazy()
    {
        MockEndpoint.instance
    }
    /**
     * Handles testing of [Request] wrappers.
     */
    override val router: MockRouter = MockRouter()


    /**
     * Implementations should handle how the client acts once socket has successfully connected
     */
    override fun onConnect()
    {

    }

    /**
     * Implementations should handle how the client acts once the socket has disconnected. Usually
     * it will just clean after itself: close any loggers it had, etc.
     */
    override fun onDisconnect()
    {

    }

    /**
     * Implementations should handle how the client connects
     * @return true, if connection succeeds
     */
    override fun connect(): Boolean
    {
        return true
    }

    /**
     * Implementations should determine themselves on whether or not the client is still connected.
     * @return true, if the client is still connected
     */
    override fun isConnected(): Boolean
    {
        return true
    }

    fun getRouters(): MockRouter
    {
        return router
    }
}