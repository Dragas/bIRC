package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Resume

open class GatewayResume(@Expose @SerializedName("d") override var data: Resume) : OPResponse<Resume>()
{
    @Expose
    @SerializedName("op")
    override val op: Int = 6
}