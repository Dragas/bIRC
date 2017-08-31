package lt.saltyjuice.dragas.chatty.v3.async.route

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.channels.SendChannel
import kotlinx.coroutines.experimental.launch
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller

open class AsyncController<Response> : Controller<Response>()
{
    private var listener: SendChannel<Response>? = null

    /**
     * Equivalent to calling writeResponse(response, false)
     */
    override fun writeResponse(response: Response)
    {
        writeResponse(response, false)
    }

    /**
     * Writes response to response listener, when [now] is true. Otherwise, response
     * is written to internal buffer which is later consumed and cleaned out.
     */
    open fun writeResponse(response: Response, now: Boolean)
    {
        if (now)
            launch(CommonPool) { listener!!.send(response) }
        else
            super.writeResponse(response)
    }

    open fun listen(listener: SendChannel<Response>)
    {
        this.listener = listener
    }
}