package lt.saltyjuice.dragas.chatty.v3.irc.feature

import lt.saltyjuice.dragas.chatty.v3.irc.IrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.controller.NicknameController
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.middleware.AuthMiddleware
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class NicknameControllerTest
{
    @Test
    fun canChangeNicknameWhenItsBad()
    {
        val request = adapter.deserialize(":server.pinger.com 433 first :nickname in use")
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(Command.NICK.value, response.command)
    }

    @Test
    fun canChangeNickname()
    {
        val request = adapter.deserialize(":!jto@tolsun.oulu.fi NICK Kilroy ")
        val response = router.consume(request)
        Assert.assertNull(response)
        Assert.assertEquals(NicknameController.currentNickname, request.arguments[0])
    }

    @Test
    fun canInitializeNickname()
    {
        val request = adapter.deserialize(":server.pinger.com NOTICE AUTH :stuff stuff stuff")
        val response = router.consume(request)
        Assert.assertNotNull(response)
        response as Response
        Assert.assertEquals(Command.NICK.value, response.command)
        Assert.assertEquals(NicknameController.currentNickname, response.arguments[0])
        Assert.assertTrue(response.otherResponses.size > 0)
    }

    companion object
    {
        @JvmStatic
        val router = IrcRouter()

        @JvmStatic
        val adapter = IrcAdapter()

        @JvmStatic
        val nicknames = arrayOf("first", "second", "third")

        @BeforeClass
        @JvmStatic
        fun init()
        {
            val settings = IrcSettings()
            settings.nicknames.addAll(nicknames)
            AuthMiddleware()
            NicknameController.initialize(router, settings)
        }
    }
}
