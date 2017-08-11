package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

class Game
{
    @SerializedName("name")
    var name: String = ""

    @SerializedName("type")
    var type: Int? = null

    @SerializedName("url")
    var url: String? = null
}