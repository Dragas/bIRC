package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

open class Embed
{
    @Expose
    @SerializedName("title")
    var title: String = ""

    @Expose
    @SerializedName("type")
    var type: String = ""

    @Expose
    @SerializedName("description")
    var description: String = ""

    @Expose
    @SerializedName("url")
    var url: String = ""

    @Expose
    @SerializedName("timestamp")
    var timestamp: Date? = null

    @Expose
    @SerializedName("color")
    var color: Int = 0

    @Expose
    @SerializedName("footer")
    var footer: Footer? = null

    @Expose
    @SerializedName("image")
    var image: Image? = null

    @Expose
    @SerializedName("thumbnail")
    var thumbnail: Thumbnail? = null

    @Expose
    @SerializedName("video")
    var video: Video? = null

    @Expose
    @SerializedName("provider")
    var provider: Provider? = null

    @Expose
    @SerializedName("author")
    var author: Author? = null

    @Expose
    @SerializedName("fields")
    var fields: ArrayList<Field> = ArrayList()
}