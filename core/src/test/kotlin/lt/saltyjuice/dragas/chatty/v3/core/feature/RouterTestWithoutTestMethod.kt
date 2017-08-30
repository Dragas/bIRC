package lt.saltyjuice.dragas.chatty.v3.core.feature

import lt.saltyjuice.dragas.chatty.v3.core.exception.RouteBuilderException
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockControllerWithoutTestMethod
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockRouter
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTestWithoutTestMethod
{
    @Test
    fun routerDoesntAddRouteWithoutTestMethod()
    {
        val count = router.getRoutess()
        Assert.assertTrue(count.isEmpty())
    }

    companion object
    {
        @JvmStatic
        val router = MockRouter()

        @JvmStatic
        @BeforeClass
        fun init()
        {
            try
            {
                router.consume(MockControllerWithoutTestMethod::class.java)
            }
            catch (err: RouteBuilderException)
            {

            }
        }
    }
}