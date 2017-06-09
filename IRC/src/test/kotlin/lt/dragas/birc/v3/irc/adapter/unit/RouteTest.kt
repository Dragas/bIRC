package lt.dragas.birc.v3.irc.adapter.unit

import lt.dragas.birc.v3.irc.adapter.IrcAdapter
import lt.dragas.birc.v3.irc.initializeRoutes
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.route.IrcRouteGroup
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * Created by mgrid on 2017-06-09.
 */
@RunWith(JUnit4::class)
class RouteTest
{
    @Test
    fun buildsRouteMapByType()
    {
        val correspondingRoutes = routemap[privateMessage.command]
        Assert.assertNotNull(correspondingRoutes)
    }

    @Test
    fun canTriggerRoute()
    {
        val correspondingRoutes = routemap[privateMessage.command]
        val canTrigger = correspondingRoutes?.let { routes ->
            var triggers = false
            routes.forEach { route ->
                if (triggers)
                    return@forEach
                triggers = route.canTrigger(privateMessage)
            }
            triggers
        }
        Assert.assertTrue(canTrigger == true)
    }

    companion object
    {
        @JvmStatic
        private lateinit var routemap: HashMap<String, ArrayList<IrcRouteGroup>>
        @JvmStatic
        private lateinit var privateMessage: Request

        @BeforeClass
        @JvmStatic
        fun initializeRoutesBeforeTests()
        {
            routemap = initializeRoutes()
            privateMessage = IrcAdapter().deserialize(":niceman1!man@AA3DA92D.A1380E30.9FA3D578.IP PRIVMSG niceman :asdf")
        }
    }
}