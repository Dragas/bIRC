package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

class MemberChunk
{
    @SerializedName("guild_id")
    var guildId: String = ""

    @SerializedName("members")
    var members: ArrayList<Member> = ArrayList()
}