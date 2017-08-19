package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockClient
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ClientTest
{

    @Test
    fun clientHasBeforeMiddlewares()
    {
        Assert.assertTrue(client.getBeforeMiddlewaress().isNotEmpty())
    }

    @Test
    fun clientHasAfterMiddlewares()
    {
        Assert.assertTrue(client.getAfterMiddlewaress().isNotEmpty())
    }

    @Test
    fun clientHasControllers()
    {
        Assert.assertTrue(client.getControllerss().isNotEmpty())
    }

    @Test
    fun clientHasRoutes()
    {
        Assert.assertTrue(client.getRouters().getRoutess().isNotEmpty())
    }

    companion object
    {
        @JvmStatic
        val client = MockClient()

        @JvmStatic
        @BeforeClass
        fun init()
        {
            client.initialize()
        }
    }
}