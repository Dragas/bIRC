package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Attachment
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("filename")
    var filename: String = ""

    @Expose
    @SerializedName("size")
    var size: Int = 0

    @Expose
    @SerializedName("url")
    var url: String = ""

    @Expose
    @SerializedName("proxy_url")
    var proxyUrl: String = ""

    @Expose
    @SerializedName("width")
    var width: Int? = null

    @Expose
    @SerializedName("height")
    var height: Int? = null
}