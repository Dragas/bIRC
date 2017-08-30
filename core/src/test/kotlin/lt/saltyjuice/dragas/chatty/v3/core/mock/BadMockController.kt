package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.*

@Before(MockBeforeMiddleware::class)
@After(MockAfterMiddleware::class)
class BadMockController() : Controller<MockResponse>()
{
    @On(MockRequest::class)
    @When("someMethod")
    @Before(MockBeforeMiddleware::class) // dont do this
    @After(MockAfterMiddleware::class) // will throw duplicate middleware exception
    fun badCallback(request: MockRequest)
    {
        writeResponse(MockResponse("", ""))
    }
}