package lt.saltyjuice.dragas.chatty.v3.core.unit

import lt.saltyjuice.dragas.chatty.v3.core.mock.MockController
import lt.saltyjuice.dragas.chatty.v3.core.mock.MockControllerWithoutTestMethod
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ControllerTest
{
    @Test
    fun controllerReturnsCallbacks()
    {
        val methods = instance.javaClass.methods
        val callbacks = instance.methods()
        Assert.assertTrue(callbacks.size <= methods.size)
    }

    @Test
    fun controllerHasTestCallback()
    {
        val callback = instance.methods()[0]
        val testCallbackName = callback.getAnnotation(When::class.java).value
        Assert.assertNotNull(instance.javaClass.methods.find { it.name == testCallbackName })
    }

    @Test
    fun controllerDoesntHaveSuchTestCallback()
    {
        val callback = MockControllerWithoutTestMethod().methods()[0]
        val testCallbackName = callback.getAnnotation(When::class.java).value
        Assert.assertNull(instance.javaClass.methods.find { it.name == testCallbackName })
    }

    companion object
    {
        @JvmStatic
        val instance = MockController()
    }
}