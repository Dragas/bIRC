package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware

class MockBeforeMiddleware : BeforeMiddleware<MockRequest>
{
    /**
     * Returns true when [Request] may be used in application.
     */
    override fun before(request: MockRequest): Boolean
    {
        return true
    }
}