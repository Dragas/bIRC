package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Reaction
{
    @Expose
    @SerializedName("user_id")
    var userId: String = ""

    @Expose
    @SerializedName("message_id")
    var messageId: String = ""

    @Expose
    @SerializedName("channel_id")
    var channelId: String = ""

    @Expose
    @SerializedName("emoji")
    var emoji: Emoji = Emoji()
}