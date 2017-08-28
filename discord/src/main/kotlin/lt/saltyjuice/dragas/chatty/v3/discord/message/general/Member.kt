package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

open class Member
{
    @Expose
    @SerializedName("user")
    var user: User = User()

    @Expose
    @SerializedName("nick")
    var nick: String? = null

    @Expose
    @SerializedName("roles")
    var roles: ArrayList<String> = ArrayList()

    @Expose
    @SerializedName("joined_at")
    var joinedAt: Date = Date()

    @Expose
    @SerializedName("deaf")
    var isDeaf: Boolean = false

    @Expose
    @SerializedName("mute")
    var isMute: Boolean = false
}