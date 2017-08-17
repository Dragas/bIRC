package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class ChangedMember : Member()
{
    @SerializedName("guild_id")
    var guildId: String = ""
}