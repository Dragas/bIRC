package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Role
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("color")
    var color: Int = 0

    @Expose
    @SerializedName("hoist")
    var isPinned: Boolean = false

    @Expose
    @SerializedName("position")
    var position: Int = 0

    @Expose
    @SerializedName("permissions")
    var permissions: Int = 0

    @Expose
    @SerializedName("managed")
    var isManaged: Boolean = false

    @Expose
    @SerializedName("mentionable")
    var isMentionable: Boolean = false
}