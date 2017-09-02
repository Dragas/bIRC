package lt.saltyjuice.dragas.chatty.v3.async.mock.io

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output

class MockEndpoint(private val requestChannel: Channel<Int>, private val responseChannel: Channel<Float>) : Input<Int, Int>, Output<Float, Float>
{
    override val adapter: MockAdapter = MockAdapter()

    override fun getRequest(): Int = runBlocking()
    {
        requestChannel.receive()
    }

    override fun writeResponse(response: Float) = runBlocking()
    {
        responseChannel.send(response)
    }
}