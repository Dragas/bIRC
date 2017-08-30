package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.*

@Before(MockBeforeMiddleware::class)
@After(MockAfterMiddleware::class)
class MockControllerWithBadMiddlewares() : Controller<MockResponse>()
{
    @On(MockRequest::class)
    @When("testMethod")
    @Before(MockBeforeMiddleware::class)
    @After(MockAfterMiddleware::class)
    fun mockGenerator(request: MockRequest)
    {
        writeResponse(MockResponse(request.fieldName, request.fieldValue))
    }

    fun testMethod(request: MockRequest): Boolean
    {
        return true
    }
}