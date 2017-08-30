package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.exception.RouteBuilderException
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

    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        val controller = MockControllerWithBadMiddlewares::class.java

        @JvmStatic
        @BeforeClass
        fun init()
        {
            try
            {
                router.consume(controller)
            }
            catch (err: RouteBuilderException)
            {

            }
        }
    }
}