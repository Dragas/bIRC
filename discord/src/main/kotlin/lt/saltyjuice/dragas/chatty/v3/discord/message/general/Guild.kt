package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.FilterLevel
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.MFALevel
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.NotificationLevel
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.VerificationLevel

open class Guild
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("icon")
    var icon: String = ""

    @Expose
    @SerializedName("name")
    var name: String = ""

    @Expose
    @SerializedName("splash")
    var splash: String = ""

    @Expose
    @SerializedName("owner_id")
    var ownerId: String = ""

    @Expose
    @SerializedName("region")
    var region: String = ""

    @Expose
    @SerializedName("afk_channel_id")
    var afkChannelId: String = ""

    @Expose
    @SerializedName("afk_timeout")
    var afkTimeout: Int = -1

    @Expose
    @SerializedName("embed_enabled")
    var isEmbeddable: Boolean = false

    @Expose
    @SerializedName("embed_channel_id")
    var embedChannelId: String = ""

    @Expose
    @SerializedName("verification_level")
    var requiredVerification: VerificationLevel = VerificationLevel.NONE

    @Expose
    @SerializedName("default_message_notifications")
    var defaultNotifications: NotificationLevel = NotificationLevel.ALL_MESSAGES

    @Expose
    @SerializedName("explicit_content_filter")
    var filterLevel: FilterLevel = FilterLevel.DISABLED

    @Expose
    @SerializedName("roles")
    var roles: ArrayList<Role> = ArrayList()

    @Expose
    @SerializedName("emojis")
    var emojis: ArrayList<Emoji> = ArrayList()

    @Expose
    @SerializedName("features")
    var features: ArrayList<String> = ArrayList()

    @Expose
    @SerializedName("mfa_level")
    var mfaLevel: MFALevel = MFALevel.NONE

    @Expose
    @SerializedName("application_id")
    var applicationId: String? = null

    @Expose
    @SerializedName("widget_enabled")
    var isWidgetEnabled: Boolean = false

    @Expose
    @SerializedName("widget_channel_id")
    var widgetChannelId: String = ""


}