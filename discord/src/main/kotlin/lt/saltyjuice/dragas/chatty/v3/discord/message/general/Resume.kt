package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Resume
{
    @SerializedName("token")
    var token: String = ""
    @SerializedName("session_id")
    var session: String = ""
    @SerializedName("seq")
    var sequenceNumber: Int = 0
}