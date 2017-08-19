package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.discord.route.DiscordRouter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseController private constructor() : Callback<Message>
{
    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     */
    override fun onFailure(call: Call<Message>, t: Throwable)
    {
        t.printStackTrace()
    }

    /**
     * Invoked for a received HTTP response.
     *
     *
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call [Response.isSuccessful] to determine if the response indicates success.
     */
    override fun onResponse(call: Call<Message>, response: Response<Message>)
    {

    }

    private fun onMessage(request: EventMessageCreate): OPResponse<*>?
    {
        val response = "NO YOU"
        val message = request.data!!
        Utility.discordAPI.createMessage(message.channelId, response).enqueue(this)
        return null
    }

    companion object
    {
        @JvmStatic
        private val instance: ResponseController = ResponseController()

        @JvmStatic
        fun initialize(router: DiscordRouter)
        {
            router.add(router.discordBuilder<EventMessageCreate, OPResponse<*>>().apply {
                this.type(EventMessageCreate::class.java)
                this.callback(instance::onMessage)
                this.testCallback {
                    it.data?.content?.contains("?test") ?: false
                }
            })
        }
    }
}