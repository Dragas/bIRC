package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Identify
{
    @Expose
    @SerializedName("token")
    var token: String = ""

    @Expose
    @SerializedName("properties")
    val properties: MutableMap<String, String> = mutableMapOf(Pair("\$os", "linux"), Pair("\$browser", "chatty/discord"), Pair("\$device", "chatty/discord"))

    @Expose
    @SerializedName("compress")
    var compress: Boolean = true

    @Expose
    @SerializedName("large_threshold")
    var threshold = 50

    @Expose
    @SerializedName("shard")
    var shard: ArrayList<Int> = ArrayList()

    @Expose
    @SerializedName("presence")
    var presence: Presence = Presence()
}