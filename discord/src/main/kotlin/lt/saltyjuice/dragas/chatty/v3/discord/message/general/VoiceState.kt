package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class VoiceState : VoiceStateUpdate()
{
    @Expose
    @SerializedName("user_id")
    var userId: String = ""

    @Expose
    @SerializedName("session_id")
    var sessionId: String = ""

    @Expose
    @SerializedName("deaf")
    var isDeaf: Boolean = false

    @Expose
    @SerializedName("mute")
    var isMuted: Boolean = false

    @Expose
    @SerializedName("suppress")
    var isSuppressed: Boolean = false
}