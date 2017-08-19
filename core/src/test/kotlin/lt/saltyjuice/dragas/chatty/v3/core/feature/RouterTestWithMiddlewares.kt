package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockClient
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockControllerWithMiddlewares
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTestWithMiddlewares
{
    @Test
    fun routeHasBeforeMiddleware()
    {
        val route = router.getRoutess()[0]
        Assert.assertTrue(route.getBeforeMiddlewaress().count() > 0)
    }

    @Test
    fun routeHasAfterMiddleware()
    {
        val route = router.getRoutess()[0]
        Assert.assertTrue(route.getAftereMiddlewaress().count() > 0)
    }

    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        val controller = MockControllerWithMiddlewares(MockClient())

        @JvmStatic
        @BeforeClass
        fun init()
        {
            router.consume(controller)
        }
    }
}