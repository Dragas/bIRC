package lt.saltyjuice.dragas.chatty.v3.async.unit

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.async.mock.controller.MockController
import org.junit.*
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
open class ControllerTest
{
    @Rule
    @JvmField
    public val timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test
    fun controllerWritesResponseNow() = runBlocking()
    {
        controller.providesModuloOfTwoWritesNow(2)
        Assert.assertEquals(0, channel.receive())
    }

    companion object
    {
        @JvmStatic
        private val controller: MockController = MockController()

        @JvmStatic
        private val channel: Channel<Int> = Channel()

        @JvmStatic
        @BeforeClass
        fun init()
        {
            controller.listen(channel)
        }

        @JvmStatic
        @AfterClass
        fun destroy()
        {
            channel.close()
        }
    }
}