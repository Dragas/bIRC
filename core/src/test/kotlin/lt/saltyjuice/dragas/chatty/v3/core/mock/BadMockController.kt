package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.*

@BeforeRequest(MockBeforeMiddleware::class)
@AfterResponse(MockAfterMiddleware::class)
class BadMockController(client: MockClient) : Controller<MockRequest, MockResponse>(client)
{
    @Callback
    @TestedBy("someMethod")
    @BeforeRequest(MockBeforeMiddleware::class) // dont do this
    @AfterResponse(MockAfterMiddleware::class) // will throw duplicate middleware exception
    fun badCallback(request: MockRequest): MockResponse
    {
        return MockResponse("", "")
    }
}