package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class VoiceStateUpdate
{
    @SerializedName("guild_id")
    var guildId: String = ""
    @SerializedName("channel_id")
    var channelId: String? = null
    @SerializedName("self_mute")
    var selfMute: Boolean = false
    @SerializedName("self_deaf")
    var selfDeaf: Boolean = false
}