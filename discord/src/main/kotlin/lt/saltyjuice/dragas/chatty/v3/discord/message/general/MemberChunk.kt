package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MemberChunk
{
    @Expose
    @SerializedName("guild_id")
    var guildId: String = ""

    @Expose
    @SerializedName("members")
    var members: ArrayList<Member> = ArrayList()
}