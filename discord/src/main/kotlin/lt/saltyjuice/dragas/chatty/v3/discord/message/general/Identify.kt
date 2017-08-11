package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

class Identify
{
    @SerializedName("token")
    var token: String = ""

    @SerializedName("properties")
    val properties: MutableMap<String, String> = mutableMapOf(Pair("\$os", "linux"), Pair("\$browser", "chatty/discord"), Pair("\$device", "chatty/discord"))

    @SerializedName("compress")
    var compress: Boolean = true

    @SerializedName("large_threshold")
    var threshold = 50

    @SerializedName("shard")
    var shard: ArrayList<Int> = ArrayList()

    @SerializedName("presence")
    var presence: StatusUpdate = StatusUpdate()
}