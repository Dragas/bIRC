package lt.saltyjuice.dragas.chatty.v3.discord.route

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.route.WebSocketRoute

open class DiscordRoute<Request : OPRequest<*>, Response : OPResponse<*>> : WebSocketRoute<Request, Response>()
{
    /*open val typeChecker : (Any) -> Boolean = {true}
    override var testCallback: (Request) -> Boolean = {true}

    */
    /**
     * Tests the request by first checking whether or not it passes the middleware test,
     * only after it does it test for actual patterns.
     */
    /*
        override fun canTrigger(request: Request): Boolean
        {
            return typeChecker.invoke(request) && super.canTrigger(request)
        }*/
}