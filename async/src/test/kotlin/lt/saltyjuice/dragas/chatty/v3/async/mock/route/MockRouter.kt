package lt.saltyjuice.dragas.chatty.v3.async.mock.route

import kotlinx.coroutines.experimental.channels.SendChannel
import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncRouter

class MockRouter(channel: SendChannel<Float>) : AsyncRouter<MockRequest, Float>(channel)
{
    override fun builder(): MockRoute.Builder
    {
        return MockRoute.Builder()
    }
}