package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class Field
{
    @SerializedName("name")
    var name: String = ""

    @SerializedName("value")
    var value: String = ""

    @SerializedName("inline")
    var inline: Boolean = false
}