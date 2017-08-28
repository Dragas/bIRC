package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Image
{
    @Expose
    @SerializedName("url")
    var url: String = ""

    @Expose
    @SerializedName("proxy_url")
    var proxyUrl: String = ""

    @Expose
    @SerializedName("height")
    var height: Int = 0

    @Expose
    @SerializedName("width")
    var width: Int = 0
}