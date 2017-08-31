package lt.saltyjuice.dragas.chatty.v3.async.mock.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import lt.saltyjuice.dragas.chatty.v3.core.adapter.Serializer

class MockAdapter : Serializer<Int, Int>, Deserializer<Int, Int>
{
    override fun serialize(any: Int): Int
    {
        return any
    }

    override fun deserialize(block: Int): Int
    {
        return block
    }
}