package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.route.Router

class MockRouter : Router<MockRequest, MockResponse>()
{
    fun getRoutess(): List<MockRoute>
    {
        return routes as List<MockRoute>
    }

    /**
     * Returns a route builder, which handles assigning middlewares, callback and test callback
     */
    override fun builder(): MockRoute.Builder
    {
        return MockRoute.Builder()
    }
}