package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

open class Message
{
    @SerializedName("id")
    var id: String = ""

    @SerializedName("channel_id")
    var channelId: String = ""

    @SerializedName("author")
    var author: User = User()

    @SerializedName("content")
    var content: String = ""

    @SerializedName("timestamp")
    var timestamp: Date = Date()

    @SerializedName("edited_timestamp")
    var editedTimestamp: Date? = null

    @SerializedName("tts")
    var isTts: Boolean = false

    @SerializedName("mention_everyone")
    var isMentioningEveryone: Boolean = false

    @SerializedName("mentions")
    var mentionedUsers: ArrayList<User> = ArrayList()

    @SerializedName("mention_roles")
    var mentionedRoles: ArrayList<String> = ArrayList()

    @SerializedName("attachments")
    var attachments: ArrayList<Attachment> = ArrayList()

    @SerializedName("embeds")
    var embeds: ArrayList<Embed> = ArrayList()


}