package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import java.util.*

open class Member
{
    @SerializedName("user")
    var user: User = User()

    @SerializedName("nick")
    var nick: String? = null

    @SerializedName("roles")
    var roles: ArrayList<String> = ArrayList()

    @SerializedName("joined_at")
    var joinedAt: Date = Date()

    @SerializedName("deaf")
    var isDeaf: Boolean = false

    @SerializedName("mute")
    var isMute: Boolean = false
}