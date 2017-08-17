package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.OverwriteType

open class Overwrite
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("type")
    var type: OverwriteType = OverwriteType.MEMBER

    @SerializedName("allow")
    var grantedPermissions: Int = 0

    @SerializedName("deny")
    var deniedPermissions: Int = 0
}