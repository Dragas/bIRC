package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class Field()
{
    @JvmOverloads
    constructor(name: String, value: String, inline: Boolean = false) : this()
    {
        this.name = name
        this.value = value
        this.inline = inline
    }

    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("value")
    var value: String = ""

    @Expose
    @SerializedName("inline")
    var inline: Boolean = false
}