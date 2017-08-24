package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class MockControllerWithoutTestMethod() : Controller<MockRequest, MockResponse>()
{
    @On(MockRequest::class)
    @When("anotherMethod")
    fun notSoMockResponseGenerator(mockRequest: MockRequest): MockResponse
    {
        return MockResponse("", "")
    }
}