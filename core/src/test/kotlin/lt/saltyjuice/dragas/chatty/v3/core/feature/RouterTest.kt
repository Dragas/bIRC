package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockController
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRequest
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRoute
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
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
        val count = router.getRoutess().isNotEmpty()
        Assert.assertTrue(count)
    }

    @Test
    fun routeHasDescription()
    {
        val description = route.getDescriptions()
        Assert.assertNotEquals("", description)
    }

    @Test
    fun routeCanBeTested()
    {
        val mockRequest = MockRequest("", "")
        Assert.assertTrue(route.canTrigger(mockRequest))
    }

    @Test
    fun routeCanRespond()
    {
        val mockRequest = MockRequest("", "")
        route.attemptTrigger(mockRequest)
        val response = route.getResponses()
        Assert.assertTrue(response.isNotEmpty())
    }

    @Test
    fun controllerDoesntStick()
    {
        val controllerInstance = route.getControllerInstance()
        route.canTrigger(MockRequest("", ""))
        val controllerInstance2 = route.getControllerInstance()
        Assert.assertNotEquals(controllerInstance, controllerInstance2)
    }

    companion object
    {
        @JvmStatic
        val route: MockRoute by lazy { getFirstRoute() }

        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        var controller = MockController::class.java

        @JvmStatic
        @BeforeClass
        fun init()
        {
            router.consume(controller)
        }

        @JvmStatic
        fun getFirstRoute(): MockRoute
        {
            return router.getRoutess()[0]
        }
    }
}