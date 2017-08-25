package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class MockController() : Controller<MockRequest, MockResponse>()
{
    @On(MockRequest::class)
    @When("mockTest")
    fun mockResponseGenerator(mockRequest: MockRequest): MockResponse
    {
        return MockResponse(mockRequest.fieldValue, mockRequest.fieldName)
    }

    fun mockTest(mockRequest: MockRequest): Boolean
    {
        return true
    }
}