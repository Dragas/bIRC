package lt.saltyjuice.dragas.chatty.v3.websocket

import lt.saltyjuice.dragas.chatty.v3.websocket.main.WebSocketCallback
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WebSocketCallbackTest
{
    @Test
    fun canCallbackBeCalledWithDescendingClass()
    {
        val barCallback = WebSocketCallback(Bar::class.java, helper::callback)
        Assert.assertFalse(barCallback.canBeCalled(Foo::class.java)) // tests for completely unrelated class
        Assert.assertFalse(barCallback.canBeCalledPartially(Foo::class.java)) // tests for completely unrelated class
        Assert.assertFalse(barCallback.canBeCalled(Nar::class.java)) // tests for descendants
        Assert.assertTrue(barCallback.canBeCalledPartially(Nar::class.java)) // tests for descendants
        Assert.assertTrue(barCallback.canBeCalled(Bar::class.java)) // tests for actual class
    }

    @Test
    fun cantBeCalledForSuperClass()
    {
        val barCallback = WebSocketCallback(Nar::class.java, helper::callback)
        Assert.assertFalse(barCallback.canBeCalled(Foo::class.java))
        Assert.assertFalse(barCallback.canBeCalledPartially(Foo::class.java))
        Assert.assertFalse(barCallback.canBeCalled(Bar::class.java))
        Assert.assertFalse(barCallback.canBeCalledPartially(Bar::class.java))
        Assert.assertTrue(barCallback.canBeCalled(Nar::class.java))
    }

    companion object
    {
        private lateinit var helper: Helper
        @BeforeClass
        @JvmStatic
        fun init()
        {
            helper = Helper()
        }
    }
}

private class Helper
{
    fun callback(foo: Foo)
    {

    }

    fun callback(bar: Bar)
    {

    }

    fun callback(nar: Nar)
    {

    }
}

private open class Foo

private open class Bar

private open class Nar : Bar()