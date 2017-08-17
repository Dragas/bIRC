package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class DeletedMessage
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("channel_id")
    var channelId: String = ""
}