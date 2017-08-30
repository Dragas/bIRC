package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class MockControllerWithoutTestMethod() : Controller<MockResponse>()
{
    @On(MockRequest::class)
    @When("anotherMethod")
    fun notSoMockResponseGenerator(mockRequest: MockRequest)
    {
        writeResponse(MockResponse("", ""))
    }
}