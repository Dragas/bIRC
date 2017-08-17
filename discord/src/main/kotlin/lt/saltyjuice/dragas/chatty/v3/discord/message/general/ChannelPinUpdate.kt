package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import java.util.*

open class ChannelPinUpdate
{
    @SerializedName("channel_id")
    var channelId: String = ""

    @SerializedName("last_pin_timestamp")
    var lastPinTimeStamp: Date = Date()
}