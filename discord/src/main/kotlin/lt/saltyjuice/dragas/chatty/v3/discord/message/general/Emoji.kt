package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Emoji
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("roles")
    var roles: ArrayList<String> = ArrayList()

    @SerializedName("required_colons")
    var needsWrapping: Boolean = false

    @SerializedName("managed")
    var managed: Boolean = false
}