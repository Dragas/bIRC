package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Resume

open class GatewayResume(@SerializedName("d") override var data: Resume) : OPResponse<Resume>()
{
    override val op: Int = 6
}