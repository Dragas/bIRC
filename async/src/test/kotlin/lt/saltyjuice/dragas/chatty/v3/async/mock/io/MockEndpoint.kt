package lt.saltyjuice.dragas.chatty.v3.async.mock.io

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output

class MockEndpoint : Input<Int, Int>, Output<Int, Int>
{
    override val adapter: MockAdapter = MockAdapter()

    override fun getRequest(): Int
    {
        return 5
    }

    override fun writeResponse(response: Int)
    {

    }
}