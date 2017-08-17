package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

open class CreatedGuild : Guild()
{
    @SerializedName("joined_at")
    var joinedAt: Date = Date()

    @SerializedName("large")
    var isLarge: Boolean = false

    @SerializedName("unavailable")
    var isUnavailable: Boolean? = null

    @SerializedName("member_count")
    var memberCount: Int = 0

    @SerializedName("voice_states")
    var voiceStates: ArrayList<VoiceState> = ArrayList()

    @SerializedName("members")
    var users: ArrayList<Member> = ArrayList()

    @SerializedName("channels")
    var channels: ArrayList<Channel> = ArrayList()

    @SerializedName("presences")
    var presences: ArrayList<Presence> = ArrayList()
}