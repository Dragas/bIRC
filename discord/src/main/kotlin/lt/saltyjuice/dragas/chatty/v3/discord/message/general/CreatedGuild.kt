package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

open class CreatedGuild : Guild()
{
    @Expose
    @SerializedName("joined_at")
    var joinedAt: Date = Date()

    @Expose
    @SerializedName("large")
    var isLarge: Boolean = false

    @Expose
    @SerializedName("unavailable")
    var isUnavailable: Boolean? = null

    @Expose
    @SerializedName("member_count")
    var memberCount: Int = 0

    @Expose
    @SerializedName("voice_states")
    var voiceStates: ArrayList<VoiceState> = ArrayList()

    @Expose
    @SerializedName("members")
    var users: ArrayList<Member> = ArrayList()

    @Expose
    @SerializedName("channels")
    var channels: ArrayList<Channel> = ArrayList()

    @Expose
    @SerializedName("presences")
    var presences: ArrayList<Presence> = ArrayList()
}