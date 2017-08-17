package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.GameType

open class Game
{
    @SerializedName("name")
    var name: String = ""

    @SerializedName("type")
    var type: GameType? = null

    @SerializedName("url")
    var url: String? = null
}