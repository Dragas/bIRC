package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter

/**
 * Handles routes asynchronously
 */
open class AsyncRouter : IrcRouter()
{
    override fun consume(request: Request): Response?
    {
        return routes.parallelStream()
                .map {
                    it.attemptTrigger(request)
                }
                .filter {
                    it != null
                }
                .collect(
                        {
                            Response("")
                        },
                        { supplier: Response, response: Response? ->
                            supplier.otherResponses.add(response!!)
                        },
                        { supplier: Response, other: Response ->
                            supplier.otherResponses.addAll(other.otherResponses)
                        })
    }
}