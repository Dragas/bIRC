package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class VoiceStateUpdate
{
    @Expose
    @SerializedName("guild_id")
    var guildId: String = ""

    @Expose
    @SerializedName("channel_id")
    var channelId: String? = null

    @Expose
    @SerializedName("self_mute")
    var isSelfMute: Boolean = false

    @Expose
    @SerializedName("self_deaf")
    var isSelfDeaf: Boolean = false
}