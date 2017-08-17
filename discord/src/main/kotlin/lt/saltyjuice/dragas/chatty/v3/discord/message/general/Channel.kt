package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.ChannelType

open class Channel
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("type")
    var type: ChannelType = ChannelType.DM

    @SerializedName("guild_id")
    var guildId: String? = null

    @SerializedName("permission_overwrites")
    var permissionOverwrites: ArrayList<Overwrite> = ArrayList()

    @SerializedName("name")
    var name: String? = null

    @SerializedName("topic")
    var topic: String? = null

    @SerializedName("last_message_id")
    var lastMessageId: String? = null

    @SerializedName("bitrate")
    var bitrate: Int? = null

    @SerializedName("user_limit")
    var userLimit: Int? = null

    @SerializedName("recipients")
    var users: ArrayList<User>? = null

    @SerializedName("icon")
    var icon: String? = null

    @SerializedName("owner_id")
    var ownerId: String? = null

    @SerializedName("application_id")
    var applicationId: String? = null
}