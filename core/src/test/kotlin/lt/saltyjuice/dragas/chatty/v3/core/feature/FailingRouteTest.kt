package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.FailingController
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRequest
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRoute
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FailingRouteTest
{

    @Test
    fun controllerSticksWhenItFailsToConsumeRequest()
    {
        failingRoute.canTrigger(MockRequest("", ""))
        val instance = failingRoute.getControllerInstance()
        Assert.assertEquals(instance, failingRoute.getControllerInstance())
    }


    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        val controller = FailingController::class.java

        @JvmStatic
        @BeforeClass
        fun init()
        {
            router.consume(controller)
        }

        @JvmStatic
        val failingRoute: MockRoute by lazy { router.getRoutess()[0] }
    }
}