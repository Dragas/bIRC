package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class PresenceUpdate : Presence()
{
    @Expose
    @SerializedName("user")
    var user: User = User()

    @Expose
    @SerializedName("roles")
    var roles: ArrayList<String> = ArrayList()

    @Expose
    @SerializedName("guild_id")
    var guildId: String = ""
}