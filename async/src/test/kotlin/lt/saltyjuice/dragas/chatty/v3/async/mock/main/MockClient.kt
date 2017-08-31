package lt.saltyjuice.dragas.chatty.v3.async.mock.main

import lt.saltyjuice.dragas.chatty.v3.async.main.AsyncClient
import lt.saltyjuice.dragas.chatty.v3.async.mock.route.MockRouter
import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output

class MockClient : AsyncClient<Int, Int, Int, Int>()
{
    override val sout: Output<Int, Int>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val router: MockRouter = MockRouter(responseChannel)
    override val sin: Input<Int, Int>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun connect(): Boolean
    {
        return true
    }

    override fun isConnected(): Boolean
    {
        return true
    }
}