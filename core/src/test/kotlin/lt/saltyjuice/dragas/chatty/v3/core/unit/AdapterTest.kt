package lt.saltyjuice.dragas.chatty.v3.core.unit

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockAdapter
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockResponse
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class AdapterTest
{
    @Test
    fun canAdapterDeserialize()
    {
        val key = "mockKey"
        val value = "mockValue"
        val request = "$key:$value"
        val pojo = adapter.deserialize(request)
        Assert.assertEquals(key, pojo.fieldName)
        Assert.assertEquals(value, pojo.fieldValue)
    }

    @Test
    fun adapterCanSerialize()
    {
        val key = "mockKey"
        val value = "mockValue"
        val mockResponse = MockResponse(key, value)
        val response = adapter.serialize(mockResponse)
        Assert.assertEquals("$key:$value", response)
    }

    companion object
    {
        lateinit var adapter: MockAdapter
        @BeforeClass
        @JvmStatic
        fun init()
        {
            adapter = MockAdapter.instance
        }
    }
}