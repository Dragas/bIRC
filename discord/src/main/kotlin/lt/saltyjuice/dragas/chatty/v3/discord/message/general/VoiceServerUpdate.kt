package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class VoiceServerUpdate
{
    @SerializedName("token")
    var token: String = ""

    @SerializedName("guild_id")
    var guildId: String = ""

    @SerializedName("endpoint")
    var endpoint: String = ""
}