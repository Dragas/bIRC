package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DeletedMessages
{
    @Expose
    @SerializedName("ids")
    var ids: ArrayList<String> = ArrayList()

    @Expose
    @SerializedName("channel_id")
    var channelId: String = ""
}