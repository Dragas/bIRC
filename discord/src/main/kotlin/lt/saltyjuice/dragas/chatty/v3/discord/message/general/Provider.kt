package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Provider
{
    @SerializedName("name")
    var name: String = ""

    @SerializedName("url")
    var url: String = ""
}