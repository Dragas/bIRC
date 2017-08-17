package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class PresenceUpdate : Presence()
{
    @SerializedName("user")
    var user: User = User()

    @SerializedName("roles")
    var roles: ArrayList<String> = ArrayList()

    @SerializedName("guild_id")
    var guildId: String = ""
}