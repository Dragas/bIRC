package lt.dragas.birc.v3.irc.adapter.integration

import lt.dragas.birc.v3.irc.adapter.IrcAdapter
import lt.dragas.birc.v3.irc.controller.PingController
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
        val request = adapter.deserialize("PING origin")
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(Command.PONG.value, response.command)
        Assert.assertEquals(request.arguments[0], response.arguments[0])
    }

    @Test
    fun canPingTarget()
    {
        val request = adapter.deserialize("PING origin target")
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(Command.PONG.value, response.command)
        Assert.assertEquals(request.arguments[1], response.arguments[0])
    }

    companion object
    {
        @JvmStatic
        val router = IrcRouter()

        @JvmStatic
        val adapter = IrcAdapter()

        @BeforeClass
        @JvmStatic
        fun init()
        {
            PingController.initialize(router)
        }
    }
}