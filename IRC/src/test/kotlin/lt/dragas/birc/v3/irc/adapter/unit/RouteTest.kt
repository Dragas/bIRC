package lt.dragas.birc.v3.irc.adapter.unit

import lt.dragas.birc.v3.core.routing.Router
import lt.dragas.birc.v3.irc.adapter.IrcAdapter
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.route.IrcRoute
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.regex.Pattern

/**
 * Created by mgrid on 2017-06-09.
 */
@RunWith(JUnit4::class)
class RouteTest
{
    @Test
    fun buildsRouteMapByType()
    {

        Assert.assertNotNull(router)
    }

    @Test
    fun canTriggerRoute()
    {
        val route = object : IrcRoute("PRIVMSG", Pattern.compile(""), { request -> Response("OK") })
        {}
        Assert.assertTrue(route.canTrigger(privateMessage))
    }

    companion object
    {
        @JvmStatic
        private var router: Router<*, *>? = null
        @JvmStatic
        private lateinit var privateMessage: Request

        @BeforeClass
        @JvmStatic
        fun initializeRoutesBeforeTests()
        {
            router = null
            privateMessage = IrcAdapter().deserialize(":niceman1!man@AA3DA92D.A1380E30.9FA3D578.IP PRIVMSG niceman :asdf")
        }
    }
}