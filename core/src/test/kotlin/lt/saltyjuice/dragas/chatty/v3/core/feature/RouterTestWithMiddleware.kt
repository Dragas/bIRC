package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockControllerWithMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTestWithMiddleware
{
    @Test
    fun routeHasBeforeMiddleware()
    {
        val route = router.getRoutess()[0]
        Assert.assertTrue(route.getBeforeMiddlewaress().isNotEmpty())
    }

    @Test
    fun routeHasAfterMiddleware()
    {
        val route = router.getRoutess()[0]
        Assert.assertTrue(route.getAftereMiddlewaress().isNotEmpty())
    }

    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        val controller = MockControllerWithMiddleware::class.java

        @JvmStatic
        @BeforeClass
        fun init()
        {
            router.consume(controller)
        }
    }
}