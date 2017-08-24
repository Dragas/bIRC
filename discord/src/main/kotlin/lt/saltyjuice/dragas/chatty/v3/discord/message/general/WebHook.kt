package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

/**
 * Used to represent a webhook.
 */
open class WebHook
{
    /**
     * the id of the webhook
     */
    @SerializedName("id")
    var id: String = ""

    /**
     * the guild id this webhook is for
     */
    @SerializedName("guild_id")
    var guildId: String? = null

    /**
     * the channel id this webhook is for
     */
    @SerializedName("channel_id")
    var channelId: String = ""

    /**
     * the user this webhook was created by (not returned when getting a webhook with its token)
     */
    @SerializedName("user")
    var user: User? = null

    /**
     * 	the default name of the webhook
     */
    @SerializedName("name")
    var name: String? = null

    /**
     * 	the default avatar of the webhook
     */
    @SerializedName("avatar")
    var avatar: String? = null

    /**
     * 	the secure token of the webhook
     */
    @SerializedName("token")
    var token: String = ""
}