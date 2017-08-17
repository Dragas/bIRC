package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Role
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("color")
    var color: Int = 0

    @SerializedName("hoist")
    var isPinned: Boolean = false

    @SerializedName("position")
    var position: Int = 0

    @SerializedName("permissions")
    var permissions: Int = 0

    @SerializedName("managed")
    var isManaged: Boolean = false

    @SerializedName("mentionable")
    var isMentionable: Boolean = false
}