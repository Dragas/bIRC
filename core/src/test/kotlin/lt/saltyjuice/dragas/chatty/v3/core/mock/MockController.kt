package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

open class MockController() : Controller<MockResponse>()
{
    @On(MockRequest::class)
    @When("mockTest")
    fun mockResponseGenerator(mockRequest: MockRequest)
    {
        writeResponse(MockResponse(mockRequest.fieldValue, mockRequest.fieldName))
    }

    open fun mockTest(mockRequest: MockRequest): Boolean
    {
        return true
    }
}