package lt.saltyjuice.dragas.chatty.v3.async.mock.main

import lt.saltyjuice.dragas.chatty.v3.async.main.AsyncClient
import lt.saltyjuice.dragas.chatty.v3.async.mock.controller.MockController
import lt.saltyjuice.dragas.chatty.v3.async.mock.io.MockEndpoint
import lt.saltyjuice.dragas.chatty.v3.async.mock.route.MockRouter
import lt.saltyjuice.dragas.chatty.v3.core.route.UsesControllers

@UsesControllers(MockController::class)
class MockClient(endpoint: MockEndpoint) : AsyncClient<Int, Int, Float, Float>()
{
    override val sout: MockEndpoint = endpoint
    override val router: MockRouter = MockRouter(responseChannel)
    override val sin: MockEndpoint = endpoint

    /*override fun onConnect()
    {
        launch(CommonPool)
        {
            writeResponse(2f)
        }
    }*/

    /*override fun writeResponse(response: Float)
    {
        super.writeResponse(response)
    }*/

    override fun connect(): Boolean
    {
        return true
    }

    override fun isConnected(): Boolean
    {
        return true
    }
}