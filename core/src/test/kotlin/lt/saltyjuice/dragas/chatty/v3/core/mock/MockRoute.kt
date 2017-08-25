package lt.saltyjuice.dragas.chatty.v3.core.mock

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.route.Route

class MockRoute : Route<MockRequest, MockResponse>()
{
    fun getBeforeMiddlewaress(): MutableCollection<BeforeMiddleware<MockRequest>>
    {
        return beforeMiddlewares
    }

    fun getAftereMiddlewaress(): MutableCollection<AfterMiddleware<MockResponse>>
    {
        return afterMiddlewares
    }

    class Builder : Route.Builder<MockRequest, MockResponse>()
    {
        /**
         * Implementations should return a raw route object which is later used in [adapt] to add all the callbacks, middlewares, etc.
         */
        override fun returnableRoute(): MockRoute
        {
            return MockRoute()
        }
    }

    fun getDescriptions(): String
    {
        return description
    }
}