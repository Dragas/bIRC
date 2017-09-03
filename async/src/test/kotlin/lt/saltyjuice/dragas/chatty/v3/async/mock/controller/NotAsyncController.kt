package lt.saltyjuice.dragas.chatty.v3.async.mock.controller

import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When

class NotAsyncController : Controller<Float>()
{
    @On(Int::class)
    @When("isEven")
    fun onEven(request: MockRequest)
    {
        writeResponse(request.value.toFloat())
    }

    fun isEven(request: MockRequest): Boolean
    {
        return request % 2 == 0
    }
}