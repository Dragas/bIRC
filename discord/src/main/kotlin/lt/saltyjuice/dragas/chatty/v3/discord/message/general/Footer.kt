package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Footer
{
    @SerializedName("text")
    var text: String = ""

    @SerializedName("icon_url")
    var iconUrl: String = ""

    @SerializedName("proxy_icon_url")
    var proxyIconUrl: String = ""
}