package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class TypingStart
{
    @SerializedName("channel_id")
    var channelId: String = ""

    @SerializedName("user_id")
    var userId: String = ""

    @SerializedName("timestamp")
    var timestamp: Int = 0
}