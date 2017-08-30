package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import lt.saltyjuice.dragas.chatty.v3.core.mock.StickyController
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StickyControllerTest
{
    @Test
    fun controllerSticks()
    {
        val route = router.getRoutess()[0]
        val controllerInstance = route.getControllerInstance()
        val controllerInstance2 = route.getControllerInstance()
        Assert.assertEquals(controllerInstance, controllerInstance2)
    }

    companion object
    {
        private var router: MockRouter = MockRouter()
        @BeforeClass
        @JvmStatic
        fun init()
        {
            router.consume(StickyController::class.java)
        }
    }
}