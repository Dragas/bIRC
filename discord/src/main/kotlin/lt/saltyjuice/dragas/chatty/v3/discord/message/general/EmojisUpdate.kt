package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class EmojisUpdate
{
    @SerializedName("guild_id")
    var guildId: String = ""

    @SerializedName("emojis")
    var emojis: ArrayList<Emoji> = ArrayList()
}