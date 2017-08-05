package lt.saltyjuice.dragas.chatty.v3.irc.feature

import lt.saltyjuice.dragas.chatty.v3.irc.adapter.IrcAdapter
import lt.saltyjuice.dragas.chatty.v3.irc.controller.ChannelController
import lt.saltyjuice.dragas.chatty.v3.irc.model.Channel
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.runners.MethodSorters


@RunWith(JUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ChannelControllerTest
{
    @Test
    fun can1CorrectlyJoinChannel()
    {
        val request = adapter.deserialize(":!bee@AA3DA92D.A1380E30.9FA3D578.IP JOIN :#channel")
        val response = router.consume(request)
        Assert.assertNull(response)
        Assert.assertNotNull(ChannelController.getChannel("#channel"))
    }

    @Test
    fun can2AssignTopicContent()
    {
        val request = adapter.deserialize(":server.pinger.com 332 ne #channel :<@Bard_Soranya> Breasts are easy to keep moist. - No Bash")
        val response = router.consume(request)
        Assert.assertNull(response)
        Assert.assertEquals(ChannelController.getChannel("#channel")?.topic?.content, request.arguments[2])
    }

    @Test
    fun can3AssignTopicOwner()
    {
        val request = adapter.deserialize(":server.pinger.com 333 ne #channel Jack 1498685302")
        val response = router.consume(request)
        Assert.assertNull(response)
        Assert.assertEquals(ChannelController.getChannel("#channel")?.topic?.setBy, request.arguments[2])
    }

    @Test
    fun can4AssignChannelUsers()
    {
        val request = adapter.deserialize(":server.pinger.com 353 ne @ #channel :ne Gypsy_Prime @Itchy Dragas @TheGoddamnedDM AndroUser2 &Jack")
        val response = router.consume(request)
        val channel = ChannelController.getChannel("#channel")
        Assert.assertNull(response)
        Assert.assertNotNull(channel)
        channel as Channel
        request.arguments[3].split(" ").forEach { name ->
            Assert.assertNotNull(channel.users.find { user -> user.name == name })
        }
    }

    @Test
    fun can5UserLeaveChannel()
    {
        val request = adapter.deserialize(":Gypsy_Prime!bee@AA3DA92D.A1380E30.9FA3D578.IP PART :#channel")
        val response = router.consume(request)
        Assert.assertNull(response)
        val channel = ChannelController.getChannel("#channel")
        Assert.assertNotNull(channel)
        channel as Channel
        val user = channel.users.find { it.name == request.nickname }
        Assert.assertNull(user)
    }

    @Test
    fun can6UserChangeNickname()
    {
        val request = adapter.deserialize(":Jack!bee@AA3DA92D.A1380E30.9FA3D578.IP NICK :Black")
        val newNickname = "Black"
        val response = router.consume(request)
        Assert.assertNull(response)
        val channel = ChannelController.getChannel("#channel") as Channel
        val user = channel.users.find { it.name.endsWith(newNickname) }
        Assert.assertNotNull(user)
    }

    @Test
    fun can7ILeaveChannel()
    {
        val request = adapter.deserialize(":!bee@AA3DA92D.A1380E30.9FA3D578.IP PART :#channel")
        val response = router.consume(request)
        Assert.assertNull(response)
        val channel = ChannelController.getChannel("#channel")
        Assert.assertNull(channel)
    }

    companion object
    {
        @JvmStatic
        val router = IrcRouter()

        @JvmStatic
        val adapter = IrcAdapter()

        @JvmStatic
        @BeforeClass
        fun initialize()
        {
            ChannelController.initialize(router)
        }
    }
}