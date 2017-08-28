package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Ban
{
    @SerializedName("reason")
    var reason: String? = null

    @SerializedName("user")
    var user: User = User()
}