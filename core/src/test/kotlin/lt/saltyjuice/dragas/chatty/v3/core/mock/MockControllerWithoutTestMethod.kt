package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Callback
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.TestedBy

class MockControllerWithoutTestMethod(client: MockClient) : Controller<MockRequest, MockResponse>(client)
{
    @Callback
    @TestedBy("anotherMethod")
    fun notSoMockResponseGenerator(mockRequest: MockRequest): MockResponse
    {
        return MockResponse("", "")
    }
}