package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Footer
{
    @Expose
    @SerializedName("text")
    var text: String = ""

    @Expose
    @SerializedName("icon_url")
    var iconUrl: String = ""

    @Expose
    @SerializedName("proxy_icon_url")
    var proxyIconUrl: String = ""
}