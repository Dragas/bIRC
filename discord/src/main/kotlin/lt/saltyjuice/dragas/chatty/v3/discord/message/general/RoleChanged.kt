package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class RoleChanged
{
    @SerializedName("guild_id")
    var guildId: String = ""

    @SerializedName("role")
    var role: Role = Role()
}