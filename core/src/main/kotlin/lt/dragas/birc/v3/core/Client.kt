package lt.dragas.birc.v3.core

import lt.dragas.birc.v3.core.io.Input
import lt.dragas.birc.v3.core.io.Output
import lt.dragas.birc.v3.core.route.RouteGroup
import java.net.Socket
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


abstract class Client<T, R>(private val routes: Array<RouteGroup<T, R>>)
{
    protected val threadPoolSize = Runtime.getRuntime().availableProcessors()
    protected val socket = Socket()
    protected val executor: ExecutorService = Executors.newFixedThreadPool(threadPoolSize)
    protected abstract val sin: Input<T>
    protected abstract val sout: Output<R>

    abstract fun connect()

    abstract fun bindSocket()
    fun run()
    {
        connect()
        onConnect()
        process()
        onDisconnect()
    }

    open protected fun onConnect()
    {
        bindSocket()
    }

    open protected fun process()
    {
        for (i in 0..threadPoolSize - 1)
        {
            submitWork()
        }
        while (!socket.isClosed)
        {
            doWork()
        }

    }

    open protected fun submitWork()
    {
        executor.submit {
            doWork()
        }
    }

    open fun doWork()
    {
        val request = sin.getRequest()
        routes.forEach {
            it.attemptTrigger(request)?.apply {
                sout.writeResponse(this)
            }
        }
    }

    open protected fun onDisconnect()
    {

    }
}