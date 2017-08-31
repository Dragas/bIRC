package lt.saltyjuice.dragas.chatty.v3.async.route

import kotlinx.coroutines.experimental.channels.SendChannel
import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import lt.saltyjuice.dragas.chatty.v3.core.route.Router

/**
 * Asynchronous implementation of core router. The difference is that you need to provide a [responseChannel],
 * which is provided to all [AsyncRoute] based routes, so that they could send back generated responses at any time.
 *
 * @see Router
 */
abstract class AsyncRouter<Request, Response>(protected open val responseChannel: SendChannel<Response>) : Router<Request, Response>()
{
    abstract override fun builder(): AsyncRoute.Builder<Request, Response>

    /**
     * Any routes being added should use this method instead of [add(Route)], as it appends [responseChannel] to that route.
     */
    override fun add(route: Route.Builder<Request, Response>)
    {
        if (route is AsyncRoute.Builder)
            route.responseChannel(responseChannel)
        super.add(route)
    }
}