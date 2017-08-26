package lt.saltyjuice.dragas.chatty.v3.discord.message

import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Embed
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import retrofit2.Callback

open class EmbedMessage
{
    var embed: Embed? = null
    var content: String = ""

    fun send(channelId: String, callback: Callback<Message>)
    {
        Utility.discordAPI.createMessage(channelId, this).enqueue(callback)
    }
}