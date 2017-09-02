package lt.saltyjuice.dragas.chatty.v3.async.mock.controller

import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class NotAsyncController : Controller<Int>()
{
    @On(Int::class)
    @When("isEven")
    fun onEven(request: Int)
    {
        writeResponse(request)
    }

    fun isEven(request: Int): Boolean
    {
        return request % 2 == 0
    }
}