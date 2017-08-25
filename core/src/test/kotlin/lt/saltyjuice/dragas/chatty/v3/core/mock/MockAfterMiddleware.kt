package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware

class MockAfterMiddleware : AfterMiddleware<MockResponse>
{
    /**
     * Returns true when [Response] can be sent to the server.
     */
    override fun after(response: MockResponse): Boolean
    {
        return true
    }
}