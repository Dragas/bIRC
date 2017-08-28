package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class VoiceServerUpdate
{
    @Expose
    @SerializedName("token")
    var token: String = ""

    @Expose
    @SerializedName("guild_id")
    var guildId: String = ""

    @Expose
    @SerializedName("endpoint")
    var endpoint: String = ""
}