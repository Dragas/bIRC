package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.exception.DuplicateMiddlewareException
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockControllerWithBadMiddlewares
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTestWithBadMiddlewares
{
    @Test
    fun routeHasBeforeMiddleware()
    {
        val route = router.getRoutess()
        Assert.assertTrue(route.isEmpty())
    }

    /*@Test
    fun routeHasAfterMiddleware()
    {
        val route = router.getRoutess()[0]
        Assert.assertTrue(route.getAftereMiddlewaress().count() > 0)
    }*/

    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        val controller = MockControllerWithBadMiddlewares()

        @JvmStatic
        @BeforeClass
        fun init()
        {
            try
            {
                router.consume(controller)
            }
            catch (err: DuplicateMiddlewareException)
            {
                println("Carry on. this is intented")
                err.printStackTrace()
            }
        }
    }
}