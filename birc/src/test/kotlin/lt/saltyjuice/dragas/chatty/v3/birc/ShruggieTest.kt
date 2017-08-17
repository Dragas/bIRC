package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.birc.controller.ShrugController
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ShruggieTest
{
    @Test
    fun canShrug()
    {
        val request = Request("").apply {
            this.arguments = listOf("#channel", "[shrug]")
            this.command = Command.PRIVMSG.value
        }
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(response.arguments[0], "#channel")
        Assert.assertEquals(response.arguments[1], "¯\\_(ツ)_/¯")
    }

    companion object
    {
        @JvmStatic
        val router = IrcRouter()

        @JvmStatic
        @BeforeClass
        fun init()
        {
            ShrugController.initialize(router)
        }
    }
}