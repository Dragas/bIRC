package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

class Hello
{
    @SerializedName("heartbeat_interval")
    var heartBeatInterval: Long = -1

    @SerializedName("_trace")
    var trace: ArrayList<String> = ArrayList()
}