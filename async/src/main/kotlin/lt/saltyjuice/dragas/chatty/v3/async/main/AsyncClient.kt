package lt.saltyjuice.dragas.chatty.v3.async.main

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncController
import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncRouter
import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller

/**
 * Asynchronous implementation of [Client].
 *
 * Supports using both [Controller] and [AsyncController].
 *
 * This implementation provides an additional field - [responseChannel], which is used to listen for responses
 * generated by triggered routes.
 */
abstract class AsyncClient<InputBlock, Request, Response, OutputBlock> : Client<InputBlock, Request, Response, OutputBlock>()
{
    /**
     * Used to build [AsyncRouter]
     */
    protected open val responseChannel = Channel<Response>(Channel.UNLIMITED)
    /**
     * Holds reference to all listener jobs provided by this client.
     */
    protected open val listenerJobs: ArrayList<Job> = ArrayList()
    /**
     * Holds the count of how many listeners for responses the should be at most.
     */
    protected open val mostJobs: Int = Runtime.getRuntime().availableProcessors()
    override abstract val router: AsyncRouter<Request, Response>

    override fun onConnect()
    {
        repeat(Math.min(Runtime.getRuntime().availableProcessors(), mostJobs))
        {
            listenerJobs.add(launch(CommonPool) { responseChannel.consumeEach(this@AsyncClient::writeResponse) })
        }
    }

    override fun onDisconnect()
    {
        listenerJobs.forEach { it.cancel() }
        listenerJobs.clear()
    }
}