package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class VoiceState
{
    @SerializedName("guild_id")
    var guildId: String? = null

    @SerializedName("channel_id")
    var channelId: String = ""

    @SerializedName("user_id")
    var userId: String = ""

    @SerializedName("session_id")
    var sessionId: String = ""

    @SerializedName("deaf")
    var isDeaf: Boolean = false

    @SerializedName("mute")
    var isMuted: Boolean = false

    @SerializedName("self_deaf")
    var isSelfDeaf: Boolean = false

    @SerializedName("self_mute")
    var isSelfMute: Boolean = false

    @SerializedName("suppress")
    var isSuppressed: Boolean = false
}