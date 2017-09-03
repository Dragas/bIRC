package lt.saltyjuice.dragas.chatty.v3.async.mock.io

import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import lt.saltyjuice.dragas.chatty.v3.core.adapter.Serializer

class MockAdapter : Serializer<Float, Float>, Deserializer<Int, MockRequest>
{
    override fun serialize(any: Float): Float
    {
        return any
    }

    override fun deserialize(block: Int): MockRequest
    {
        return MockRequest(block)
    }
}