package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.OverwriteType

open class Overwrite
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("type")
    var type: OverwriteType = OverwriteType.MEMBER

    @Expose
    @SerializedName("allow")
    var grantedPermissions: Int = 0

    @Expose
    @SerializedName("deny")
    var deniedPermissions: Int = 0
}