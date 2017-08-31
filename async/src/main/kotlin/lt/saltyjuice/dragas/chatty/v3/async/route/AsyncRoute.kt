package lt.saltyjuice.dragas.chatty.v3.async.route

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.channels.actor
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import java.lang.reflect.Method

/**
 *
 * Asynchronous route, that implements techniques to asynchronously receive responses as well as
 *
 * @see Route
 */
open class AsyncRoute<Request, Response> : Route<Request, Response>()
{
    protected open lateinit var responseChannel: SendChannel<Response>
    protected open val listener = actor<Response>(CommonPool)
    {
        for (response in channel)
            this@AsyncRoute.attemptRespond(response)
    }

    override fun getControllerInstance(): Controller<Response>
    {
        return super.getControllerInstance().apply { if (this is AsyncController<Response>) listen(listener) }
    }

    /**
     * Tests [response] if it can be responded with and pushes it to [responseChannel]
     */
    override fun attemptRespond(response: Response)
    {
        launch(CommonPool)
        {
            if (canRespond(response)) responseChannel.send(response)
        }
    }

    /**
     * In case of application shut down, this method should be called to clean this route's listener.
     */
    open fun close()
    {
        listener.cancel()
    }

    /**
     * Returns an empty list, since it isn't necessary anymore.
     */
    override fun getResponses(): List<Response>
    {
        return listOf()
    }

    abstract class Builder<Request, Response> : Route.Builder<Request, Response>()
    {
        protected open var mChannel: SendChannel<Response>? = null

        abstract override fun returnableRoute(): AsyncRoute<Request, Response>

        override fun adapt(route: Route<Request, Response>): AsyncRoute<Request, Response>
        {
            val adapted = super.adapt(route) as AsyncRoute<Request, Response>
            adapted.responseChannel = this.mChannel!!
            return adapted
        }

        override fun after(clazz: Class<out AfterMiddleware<Response>>): AsyncRoute.Builder<Request, Response>
        {
            return super.after(clazz) as AsyncRoute.Builder
        }

        override fun before(clazz: Class<out BeforeMiddleware<Request>>): AsyncRoute.Builder<Request, Response>
        {
            return super.before(clazz) as AsyncRoute.Builder
        }

        override fun callback(callback: (Route<Request, Response>, Request) -> Unit): AsyncRoute.Builder<Request, Response>
        {
            return super.callback(callback) as AsyncRoute.Builder
        }

        override fun consume(controller: Class<out Controller<Response>>, method: Method): AsyncRoute.Builder<Request, Response>
        {
            return super.consume(controller, method) as AsyncRoute.Builder
        }

        override fun controller(clazz: Class<out Controller<Response>>): AsyncRoute.Builder<Request, Response>
        {
            return super.controller(clazz) as AsyncRoute.Builder
        }

        override fun description(string: String): AsyncRoute.Builder<Request, Response>
        {
            return super.description(string) as AsyncRoute.Builder
        }

        override fun testCallback(callback: (Route<Request, Response>, Request) -> Boolean): AsyncRoute.Builder<Request, Response>
        {
            return super.testCallback(callback) as AsyncRoute.Builder
        }

        open fun responseChannel(channel: SendChannel<Response>): AsyncRoute.Builder<Request, Response>
        {
            this.mChannel = channel
            return this
        }
    }
}