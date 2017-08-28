package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.GameType

open class Game
{
    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("type")
    var type: GameType? = null

    @Expose
    @SerializedName("url")
    var url: String? = null
}