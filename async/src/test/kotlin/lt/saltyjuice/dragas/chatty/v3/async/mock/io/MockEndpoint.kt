package lt.saltyjuice.dragas.chatty.v3.async.mock.io

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output

class MockEndpoint(private val requestChannel: Channel<MockRequest>, private val responseChannel: Channel<Float>) : Input<Int, MockRequest>, Output<Float, Float>
{
    override val adapter: MockAdapter = MockAdapter()

    override fun getRequest(): MockRequest = runBlocking()
    {
        requestChannel.receive()
    }

    override fun writeResponse(response: Float) = runBlocking()
    {
        responseChannel.send(response)
    }
}