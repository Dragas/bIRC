package lt.saltyjuice.dragas.chatty.v3.async.unit

import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.runBlocking
import lt.saltyjuice.dragas.chatty.v3.async.mock.io.MockEndpoint
import lt.saltyjuice.dragas.chatty.v3.async.mock.main.MockClient
import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.junit.rules.Timeout
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.TimeUnit

@RunWith(JUnit4::class)
class ClientTest
{
    @Rule
    @JvmField
    val timeout = Timeout(1000, TimeUnit.MILLISECONDS)

    @Test
    fun clientIsCapableOfConsumeAndResponding() = runBlocking()
    {
        requestChannel.send(MockRequest(5))
        client.run()
        Assert.assertEquals(1.0f, responseChannel.receive())
    }

    companion object
    {
        private lateinit var client: MockClient
        private val responseChannel: Channel<Float> = Channel(1)
        private val requestChannel: Channel<MockRequest> = Channel(1)
        @BeforeClass
        @JvmStatic
        fun init()
        {
            client = MockClient(MockEndpoint(requestChannel, responseChannel))
            client.initialize()
            client.onConnect()
        }
    }
}