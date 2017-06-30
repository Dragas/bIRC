package lt.dragas.birc.v3.irc.feature

import lt.dragas.birc.v3.irc.adapter.IrcAdapter
import lt.dragas.birc.v3.irc.controller.ChannelController
import lt.dragas.birc.v3.irc.route.IrcRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ChannelControllerTest
{

    @Test
    fun canCorrectlyJoinChannel()
    {
        val request = adapter.deserialize(":!bee@AA3DA92D.A1380E30.9FA3D578.IP JOIN :#channel")
        val response = router.consume(request)
        Assert.assertNull(response)
        Assert.assertTrue(ChannelController.instance.channels.contains("#channel"))
    }

    @Test
    fun canAssignTopicContentCorrectly()
    {
        val request = adapter.deserialize(":server.pinger.com 332 ne #channel :<@Bard_Soranya> Breasts are easy to keep moist. - No Bash")
        val response = router.consume(request)
        Assert.assertNull(response)
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