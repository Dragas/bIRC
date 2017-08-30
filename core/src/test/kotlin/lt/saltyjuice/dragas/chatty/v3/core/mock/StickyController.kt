package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.Singleton
import lt.saltyjuice.dragas.chatty.v3.core.route.When

@Singleton
class StickyController : MockController()
{
    @When("testTest")
    @On(MockRequest::class)
    fun mockMethod(mockRequest: MockRequest): MockResponse
    {
        return MockResponse("", "")
    }

    fun testTest(mockRequest: MockRequest): Boolean
    {
        return true
    }
}