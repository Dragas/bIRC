package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Reaction
{
    @SerializedName("user_id")
    var userId: String = ""

    @SerializedName("message_id")
    var messageId: String = ""

    @SerializedName("channel_id")
    var channelId: String = ""

    @SerializedName("emoji")
    var emoji: Emoji = Emoji()
}