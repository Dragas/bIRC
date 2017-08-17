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
    var timestamp: Date = Date()

    @SerializedName("color")
    var color: Int = 0

    @SerializedName("footer")
    var footer: Footer = Footer()

    @SerializedName("image")
    var image: Image = Image()

    @SerializedName("thumbnail")
    var thumbnail: Thumbnail = Thumbnail()

    @SerializedName("video")
    var video: Video = Video()

    @SerializedName("provider")
    var provider: Provider = Provider()

    @SerializedName("author")
    var author: Author = Author()

    @SerializedName("fields")
    var fields: ArrayList<Field> = ArrayList()
}