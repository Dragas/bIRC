package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Attachment
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("filename")
    var filename: String = ""

    @SerializedName("size")
    var size: Int = 0

    @SerializedName("url")
    var url: String = ""

    @SerializedName("proxy_url")
    var proxyUrl: String = ""

    @SerializedName("width")
    var width: Int? = null

    @SerializedName("height")
    var height: Int? = null
}