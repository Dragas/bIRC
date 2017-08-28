package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Author
{
    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("icon_url")
    var iconUrl: String = ""

    @Expose
    @SerializedName("proxy_icon_url")
    var proxyIconUrl: String = ""

    @Expose
    @SerializedName("url")
    var url: String = ""
}