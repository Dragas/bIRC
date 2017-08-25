package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.*

@Before(MockBeforeMiddleware::class)
@After(MockAfterMiddleware::class)
class MockControllerWithGlobalMiddlewares : Controller<MockRequest, MockResponse>()
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