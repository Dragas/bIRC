package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Emoji
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("roles")
    var roles: ArrayList<String> = ArrayList()

    @Expose
    @SerializedName("required_colons")
    var needsWrapping: Boolean = false

    @Expose
    @SerializedName("managed")
    var managed: Boolean = false
}