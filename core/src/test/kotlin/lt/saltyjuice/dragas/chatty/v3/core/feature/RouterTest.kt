package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.*
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTest
{

    @Test
    fun routerHasRoutes()
    {
        val count = router.getRoutess().count()
        Assert.assertTrue(count > 0)
    }

    @Test
    fun routeHasDescription()
    {
        val description = getRoute().getDescriptions()
        Assert.assertNotEquals("", description)
    }

    @Test
    fun routeCanBeTested()
    {
        val mockRequest = MockRequest("", "")
        Assert.assertTrue(getRoute().canTrigger(mockRequest))
    }

    @Test
    fun routeCanResponse()
    {
        val mockRequest = MockRequest("", "")
        val response = getRoute().attemptTrigger(mockRequest)
        Assert.assertNotNull(response)
    }

    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        val controller = MockController(MockClient())

        @JvmStatic
        @BeforeClass
        fun init()
        {
            router.consume(controller)
        }

        @JvmStatic
        fun getRoute(): MockRoute
        {
            return router.getRoutess()[0]
        }
    }
}