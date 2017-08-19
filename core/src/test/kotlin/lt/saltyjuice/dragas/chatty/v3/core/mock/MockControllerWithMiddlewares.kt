package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.*

class MockControllerWithMiddlewares(client: MockClient) : Controller<MockRequest, MockResponse>(client)
{
    @Callback
    @TestedBy("testMethod")
    @BeforeRequest(MockBeforeMiddleware::class)
    @AfterResponse(MockAfterMiddleware::class)
    fun mockGenerator(request: MockRequest): MockResponse
    {
        return MockResponse(request.fieldName, request.fieldValue)
    }

    fun testMethod(request: MockRequest): Boolean
    {
        return true
    }
}