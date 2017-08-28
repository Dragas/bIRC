package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Ban
{
    @Expose
    @SerializedName("reason")
    var reason: String? = null

    @Expose
    @SerializedName("user")
    var user: User = User()
}