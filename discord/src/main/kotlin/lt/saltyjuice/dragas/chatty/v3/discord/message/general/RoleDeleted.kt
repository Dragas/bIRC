package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class RoleDeleted
{
    @SerializedName("guild_id")
    var guildId: String = ""

    @SerializedName("role_id")
    var roleId: String = ""
}