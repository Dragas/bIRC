package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.GuildMembers

open class GatewayRequestGuildMembers(@Expose @SerializedName("d") override var data: GuildMembers) : OPResponse<GuildMembers>()
{
    @Expose
    @SerializedName("op")
    override val op: Int = 8
}