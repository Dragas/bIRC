package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Ready : Debuggable()
{
    @Expose
    @SerializedName("v")
    var version: Int = -1

    @Expose
    @SerializedName("user")
    var user: User? = null

    @Expose
    @SerializedName("private_channels")
    var privateMessageChannels: ArrayList<Channel> = ArrayList()

    @Expose
    @SerializedName("guilds")
    var guilds: ArrayList<Guild> = ArrayList()

    @Expose
    @SerializedName("session_id")
    var sessionId: String = ""
}