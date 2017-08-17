package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class VoiceState : VoiceStateUpdate()
{
    @SerializedName("user_id")
    var userId: String = ""

    @SerializedName("session_id")
    var sessionId: String = ""

    @SerializedName("deaf")
    var isDeaf: Boolean = false

    @SerializedName("mute")
    var isMuted: Boolean = false

    @SerializedName("suppress")
    var isSuppressed: Boolean = false
}