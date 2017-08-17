package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Ready : Debuggable()
{
    @SerializedName("v")
    var version: Int = -1

    @SerializedName("user")
    var user: User? = null

    @SerializedName("private_channels")
    var privateMessageChannels: ArrayList<Channel> = ArrayList()

    @SerializedName("guilds")
    var guilds: ArrayList<Guild> = ArrayList()

    @SerializedName("session_id")
    var sessionId: String = ""
}