package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Callback
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.TestedBy

class MockController(client: MockClient) : Controller<MockRequest, MockResponse>(client)
{
    @Callback
    @TestedBy("mockTest")
    fun mockResponseGenerator(mockRequest: MockRequest): MockResponse
    {
        return MockResponse(mockRequest.fieldValue, mockRequest.fieldName)
    }

    fun mockTest(mockRequest: MockRequest): Boolean
    {
        return true
    }

    @Callback
    @TestedBy("anotherMethod")
    fun notSoMockResponseGenerator(mockRequest: MockRequest): MockResponse
    {
        return MockResponse(mockRequest.fieldName, mockRequest.fieldValue)
    }
}