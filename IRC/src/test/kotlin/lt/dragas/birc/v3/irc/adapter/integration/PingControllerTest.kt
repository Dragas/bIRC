package lt.dragas.birc.v3.irc.adapter.integration

import lt.dragas.birc.v3.irc.controller.PingController
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.route.Command
import lt.dragas.birc.v3.irc.route.IrcRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test


class PingControllerTest
{

    @Test
    fun canPingOrigin()
    {
        val request = getRequest()
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(Command.PONG.value, response.command)
        Assert.assertEquals(request.arguments[0], response.arguments[0])
    }

    @Test
    fun canPingTarget()
    {
        val request = getRequest()
        request.arguments.apply {
            this as ArrayList
            add("secondargument")
        }
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(Command.PONG.value, response.command)
        Assert.assertEquals(request.arguments[1], response.arguments[0])
    }

    fun getRequest(): Request
    {
        val request = Request("")
        request.command = Command.PING.value
        request.arguments = ArrayList()
        request.arguments.apply {
            this as ArrayList
            add("firstargument")
        }
        return request
    }

    companion object
    {
        @JvmStatic
        val router = IrcRouter()

        @BeforeClass
        @JvmStatic
        fun init()
        {
            PingController.initialize(router)
        }
    }
}