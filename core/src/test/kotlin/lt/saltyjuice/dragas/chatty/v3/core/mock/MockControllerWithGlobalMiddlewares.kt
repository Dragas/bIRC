package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.*

@BeforeRequest(MockBeforeMiddleware::class)
@AfterResponse(MockAfterMiddleware::class)
class MockControllerWithGlobalMiddlewares(client: MockClient) : Controller<MockRequest, MockResponse>(client)
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
}