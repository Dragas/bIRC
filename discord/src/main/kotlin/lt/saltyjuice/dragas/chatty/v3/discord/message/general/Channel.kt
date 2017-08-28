package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.ChannelType

open class Channel
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("type")
    var type: ChannelType = ChannelType.DM

    @Expose
    @SerializedName("guild_id")
    var guildId: String? = null

    @Expose
    @SerializedName("permission_overwrites")
    var permissionOverwrites: ArrayList<Overwrite> = ArrayList()

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("topic")
    var topic: String? = null

    @Expose
    @SerializedName("last_message_id")
    var lastMessageId: String? = null

    @Expose
    @SerializedName("bitrate")
    var bitrate: Int? = null

    @Expose
    @SerializedName("user_limit")
    var userLimit: Int? = null

    @Expose
    @SerializedName("recipients")
    var users: ArrayList<User> = ArrayList()

    @Expose
    @SerializedName("icon")
    var icon: String? = null

    @Expose
    @SerializedName("owner_id")
    var ownerId: String? = null

    @Expose
    @SerializedName("application_id")
    var applicationId: String? = null
}