package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.Status

class StatusUpdate
{
    @SerializedName("since")
    var since: Int? = null

    @SerializedName("game")
    var game: Game? = null

    @SerializedName("status")
    var status: Status = Status.ONLINE

    @SerializedName("afk")
    var afk: Boolean = false
}