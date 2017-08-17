package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.FilterLevel
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.MFALevel
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.NotificationLevel
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.VerificationLevel

open class Guild
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("icon")
    var icon: String = ""

    @SerializedName("name")
    var name: String = ""

    @SerializedName("splash")
    var splash: String = ""

    @SerializedName("owner_id")
    var ownerId: String = ""

    @SerializedName("region")
    var region: String = ""

    @SerializedName("afk_channel_id")
    var afkChannelId: String = ""

    @SerializedName("afk_timeout")
    var afkTimeout: Int = -1

    @SerializedName("embed_enabled")
    var isEmbeddable: Boolean = false

    @SerializedName("embed_channel_id")
    var embedChannelId: String = ""

    @SerializedName("verification_level")
    var requiredVerification: VerificationLevel = VerificationLevel.NONE

    @SerializedName("default_message_notifications")
    var defaultNotifications: NotificationLevel = NotificationLevel.ALL_MESSAGES

    @SerializedName("explicit_content_filter")
    var filterLevel: FilterLevel = FilterLevel.DISABLED

    @SerializedName("roles")
    var roles: ArrayList<Role> = ArrayList()

    @SerializedName("emojis")
    var emojis: ArrayList<Emoji> = ArrayList()

    @SerializedName("features")
    var features: ArrayList<String> = ArrayList()

    @SerializedName("mfa_level")
    var mfaLevel: MFALevel = MFALevel.NONE

    @SerializedName("application_id")
    var applicationId: String? = null

    @SerializedName("widget_enabled")
    var isWidgetEnabled: Boolean = false

    @SerializedName("widget_channel_id")
    var widgetChannelId: String = ""


}