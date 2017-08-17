package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.GuildMembers

open class GatewayRequestGuildMembers(@SerializedName("d") override var data: GuildMembers) : OPResponse<GuildMembers>()
{
    override val op: Int = 8
}