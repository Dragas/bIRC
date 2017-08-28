package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class User
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("username")
    var username: String = ""

    @Expose
    @SerializedName("discriminator")
    var discriminator: String = ""

    @Expose
    @SerializedName("avatar")
    var avatar: String = ""

    @Expose
    @SerializedName("bot")
    var isBot: Boolean = false

    @Expose
    @SerializedName("mfa_enabled")
    var isTwoFactorAuthentificationEnabled: Boolean = false

    @Expose
    @SerializedName("verified")
    var isVerified: Boolean = false

    @Expose
    @SerializedName("email")
    var email: String = ""
}