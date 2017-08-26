package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

open class Embed
{
    @SerializedName("title")
    var title: String = ""

    @SerializedName("type")
    var type: String = ""

    @SerializedName("description")
    var description: String = ""

    @SerializedName("url")
    var url: String = ""

    @SerializedName("timestamp")
    var timestamp: Date? = null

    @SerializedName("color")
    var color: Int = 0

    @SerializedName("footer")
    var footer: Footer? = null

    @SerializedName("image")
    var image: Image? = null

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null

    @SerializedName("video")
    var video: Video? = null

    @SerializedName("provider")
    var provider: Provider? = null

    @SerializedName("author")
    var author: Author? = null

    @SerializedName("fields")
    var fields: ArrayList<Field> = ArrayList()
}