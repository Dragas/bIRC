package lt.saltyjuice.dragas.chatty.v3.async.mock.controller

import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncController
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class MockController : AsyncController<Float>()
{
    @On(MockRequest::class)
    @When("isEven")
    fun provideModuloOfTwoWritesLater(request: MockRequest)
    {
        writeResponse((request % 2).toFloat())
    }

    fun isEven(request: MockRequest): Boolean
    {
        return request % 2 == 0
    }

    @On(MockRequest::class)
    @When("isOdd")
    fun providesModuloOfTwoWritesNow(request: MockRequest)
    {
        writeResponse((request % 2).toFloat(), true)
    }

    fun isOdd(request: MockRequest): Boolean
    {
        return request % 2 != 0
    }
}