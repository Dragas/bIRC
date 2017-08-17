package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class MessageChannelPair
{
    @SerializedName("channel_id")
    var channelId: String = ""

    @SerializedName("messageId")
    var messageId: String = ""
}