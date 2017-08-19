package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.io.Input
import lt.saltyjuice.dragas.chatty.v3.core.io.Output

class MockEndpoint private constructor() : Input<String, MockRequest>, Output<MockResponse, String>
{
    /**
     * Used to deserialize [InputBlock] type requests from server to something more usable by implementations
     */
    override val adapter: MockAdapter = MockAdapter.instance

    /**
     * Returns a request from provided adapter. Do note that the implementing class should
     * also implement a method of getting actual data for this.
     */
    override fun getRequest(): MockRequest
    {
        val message = "$key:$value"
        return adapter.deserialize(message)
    }

    /**
     * Writes response to server after testing it against global middlewares provided in [middlewares]
     * @param response preformatted message that is sent to server.
     */
    override fun writeResponse(response: MockResponse)
    {
        adapter.serialize(response)
    }

    fun getResponse(response: MockResponse): String
    {
        return adapter.serialize(response)
    }

    companion object
    {
        @JvmStatic
        val instance: MockEndpoint = MockEndpoint()

        @JvmStatic
        val key: String = "mockKey"

        @JvmStatic
        val value: String = "mockValue"
    }
}