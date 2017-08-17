package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Hello : Debuggable()
{
    @SerializedName("heartbeat_interval")
    var heartBeatInterval: Long = -1
}