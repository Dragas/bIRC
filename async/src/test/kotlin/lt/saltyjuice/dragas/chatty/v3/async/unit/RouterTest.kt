package lt.saltyjuice.dragas.chatty.v3.async.unit

import kotlinx.coroutines.experimental.channels.Channel
import lt.saltyjuice.dragas.chatty.v3.async.mock.controller.NotAsyncController
import lt.saltyjuice.dragas.chatty.v3.async.mock.route.MockRouter
import lt.saltyjuice.dragas.chatty.v3.core.exception.RouteBuilderException
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RouterTest
{
    @Test
    fun routerCantBuildNotAsyncRoute()
    {
        shouldThrow(RouteBuilderException::class.java)
        {
            router.consume(controller)
        }
    }

    fun shouldThrow(vararg clazz: Class<out Throwable>, test: (() -> Unit))
    {
        var threw = false
        try
        {
            test()
        }
        catch (err: Throwable)
        {
            clazz.forEach { if (it.isAssignableFrom(err.javaClass)) threw = true else throw err }
        }

        Assert.assertTrue(threw)
    }

    companion object
    {
        private lateinit var router: MockRouter
        private val channel = Channel<Float>()
        private val controller = NotAsyncController::class.java
        @BeforeClass
        @JvmStatic
        fun init()
        {
            router = MockRouter(channel)
        }
    }
}