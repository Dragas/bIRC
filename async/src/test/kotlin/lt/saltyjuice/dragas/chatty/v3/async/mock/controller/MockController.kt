package lt.saltyjuice.dragas.chatty.v3.async.mock.controller

import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncController
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class MockController : AsyncController<Int>()
{
    @On(Int::class)
    @When("isEven")
    fun provideModuloOfTwoWritesLater(request: Int)
    {
        writeResponse(request % 2)
    }

    fun isEven(request: Int): Boolean
    {
        return request % 2 == 0
    }

    @On(Int::class)
    @When("isOdd")
    fun providesModuloOfTwoWritesNow(request: Int)
    {
        writeResponse(request % 2, true)
    }

    fun isOdd(request: Int): Boolean
    {
        return request % 2 != 0
    }
}