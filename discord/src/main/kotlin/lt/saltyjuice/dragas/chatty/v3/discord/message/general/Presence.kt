package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.Status

open class Presence
{
    @Expose
    @SerializedName("since")
    var since: Int? = null

    @Expose
    @SerializedName("game")
    var game: Game = Game()

    @Expose
    @SerializedName("status")
    var status: Status = Status.ONLINE

    @Expose
    @SerializedName("afk")
    var afk: Boolean = false
}