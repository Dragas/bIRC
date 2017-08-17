package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Image
{
    @SerializedName("url")
    var url: String = ""

    @SerializedName("proxy_url")
    var proxyUrl: String = ""

    @SerializedName("height")
    var height: Int = 0

    @SerializedName("width")
    var width: Int = 0
}