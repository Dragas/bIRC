package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class TypingStart
{
    @Expose
    @SerializedName("channel_id")
    var channelId: String = ""

    @Expose
    @SerializedName("user_id")
    var userId: String = ""

    @Expose
    @SerializedName("timestamp")
    var timestamp: Int = 0
}