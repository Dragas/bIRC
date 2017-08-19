package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.route.AfterResponse
import lt.saltyjuice.dragas.chatty.v3.core.route.BeforeRequest
import lt.saltyjuice.dragas.chatty.v3.core.route.UsesControllers


@BeforeRequest(MockBeforeMiddleware::class)
@AfterResponse(MockAfterMiddleware::class)
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
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Implementations should handle how the client acts once the socket has disconnected. Usually
     * it will just clean after itself: close any loggers it had, etc.
     */
    override fun onDisconnect()
    {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Implementations should handle how the client connects
     * @return true, if connection succeeds
     */
    override fun connect(): Boolean
    {
        return true//TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Implementations should determine themselves on whether or not the client is still connected.
     * @return true, if the client is still connected
     */
    override fun isConnected(): Boolean
    {
        return true //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getBeforeMiddlewaress(): MutableCollection<BeforeMiddleware<MockRequest>>
    {
        return beforeMiddlewares
    }

    fun getAfterMiddlewaress(): MutableCollection<AfterMiddleware<MockResponse>>
    {
        return afterMiddlewares
    }

    fun getControllerss(): MutableCollection<MockController>
    {
        return controllers as MutableCollection<MockController>
    }

    fun getRouters(): MockRouter
    {
        return router
    }
}