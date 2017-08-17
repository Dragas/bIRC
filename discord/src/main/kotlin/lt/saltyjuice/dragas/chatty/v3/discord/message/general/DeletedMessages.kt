package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

class DeletedMessages
{
    @SerializedName("ids")
    var ids: ArrayList<String> = ArrayList()

    @SerializedName("channel_id")
    var channelId: String = ""
}