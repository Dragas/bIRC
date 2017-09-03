package lt.saltyjuice.dragas.chatty.v3.async.unit

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.async.mock.controller.MockController
import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.async.mock.route.MockRoute
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class RouteTest
{
    @Rule
    @JvmField
    public val timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test
    fun routeRespondsAsynchronously() = runBlocking()
    {
        buildAsyncRoute().attemptTrigger(MockRequest(3))
        Assert.assertEquals(1.0f, channel.receive())
    }

    @Test
    fun routeRespondsSynchonously() = runBlocking()
    {
        buildSyncRoute().attemptTrigger(MockRequest(2))
        Assert.assertEquals(0.0f, channel.receive())
    }

    companion object
    {
        @JvmStatic
        private val channel: Channel<Float> = Channel()

        @JvmStatic
        private val builder: MockRoute.Builder by lazy()
        {
            MockRoute.Builder().apply()
            {
                controller(MockController::class.java)
                responseChannel(channel)
                singleton(false)
            }
        }

        @JvmStatic
        fun buildAsyncRoute(): MockRoute
        {
            return builder.apply()
            {
                testCallback()
                { route, i ->
                    val controller = route.getControllerInstance() as MockController
                    controller.isOdd(i)
                }
                callback()
                { route, i ->
                    val controller = route.getControllerInstance() as MockController
                    controller.providesModuloOfTwoWritesNow(i)
                }
            }.build() as MockRoute
        }

        @JvmStatic
        fun buildSyncRoute(): MockRoute
        {
            return builder.apply()
            {
                testCallback()
                { route, i ->
                    val controller = route.getControllerInstance() as MockController
                    controller.isEven(i)
                }
                callback()
                { route, i ->
                    val controller = route.getControllerInstance() as MockController
                    controller.providesModuloOfTwoWritesNow(i)
                }
            }.build() as MockRoute
        }

        @JvmStatic
        @AfterClass
        fun destroy()
        {
            channel.close()
        }
    }
}