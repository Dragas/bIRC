package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockControllerWithGlobalMiddlewares
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRoute
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTestWithControllerMiddlewares
{
    @Test
    fun routeHasControllerBeforeMiddleware()
    {
        val route = getRoute()
        Assert.assertTrue(route.getBeforeMiddlewaress().isNotEmpty())
    }

    @Test
    fun routeHasControllerAfterMiddleware()
    {
        val route = getRoute()
        Assert.assertTrue(route.getAftereMiddlewaress().isNotEmpty())
    }


    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        @BeforeClass
        fun init()
        {
            router.consume(MockControllerWithGlobalMiddlewares::class.java)
        }

        fun getRoute(): MockRoute
        {
            return router.getRoutess()[0]
        }
    }
}