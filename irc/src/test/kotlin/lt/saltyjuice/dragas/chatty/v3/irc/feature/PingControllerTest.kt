package lt.saltyjuice.dragas.chatty.v3.irc.feature

import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.controller.PingController
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
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