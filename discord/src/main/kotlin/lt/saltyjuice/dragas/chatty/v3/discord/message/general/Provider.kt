package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Provider
{
    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("url")
    var url: String = ""
}