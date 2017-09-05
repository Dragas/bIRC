package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ChannelBuilder(id: String)
{
    @Expose
    @SerializedName("recipient_id")
    var usarId: String = id
}