package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class DeletedMessage
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("channel_id")
    var channelId: String = ""
}