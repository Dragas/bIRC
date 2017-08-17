package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class User
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("username")
    var username: String = ""

    @SerializedName("discriminator")
    var discriminator: String = ""

    @SerializedName("avatar")
    var avatar: String = ""

    @SerializedName("bot")
    var isBot: Boolean = false

    @SerializedName("mfa_enabled")
    var isTwoFactorAuthentificationEnabled: Boolean = false

    @SerializedName("verified")
    var isVerified: Boolean = false

    @SerializedName("email")
    var email: String = ""
}