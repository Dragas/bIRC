package lt.saltyjuice.dragas.chatty.v3.core.unit

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockEndpoint
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockResponse
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class EndpointTest
{
    @Test
    fun endpointReturnsRequest()
    {
        val request = instance.getRequest()
        Assert.assertEquals(request.fieldName, MockEndpoint.key)
        Assert.assertEquals(request.fieldValue, MockEndpoint.value)
    }

    @Test
    fun endpointSerializesResponse()
    {
        val response = MockResponse("foo", "bar")
        val message = instance.getResponse(response)
        Assert.assertEquals(message, "foo:bar")
    }

    companion object
    {
        lateinit var instance: MockEndpoint

        @BeforeClass
        @JvmStatic
        fun init()
        {
            instance = MockEndpoint.instance
        }
    }
}