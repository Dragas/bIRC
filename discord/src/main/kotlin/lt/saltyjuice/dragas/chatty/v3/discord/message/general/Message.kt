package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import java.util.*
import kotlin.collections.ArrayList

open class Message
{
    @Expose
    @SerializedName("id")
    var id: String = ""

    @Expose
    @SerializedName("channel_id")
    var channelId: String = ""

    @Expose
    @SerializedName("author")
    var author: User = User()

    @Expose
    @SerializedName("content")
    var content: String = ""

    @Expose
    @SerializedName("timestamp")
    var timestamp: Date = Date()

    @Expose
    @SerializedName("edited_timestamp")
    var editedTimestamp: Date? = null

    @Expose
    @SerializedName("tts")
    var isTts: Boolean = false

    @Expose
    @SerializedName("mention_everyone")
    var isMentioningEveryone: Boolean = false

    @Expose
    @SerializedName("mentions")
    var mentionedUsers: ArrayList<User> = ArrayList()

    @Expose
    @SerializedName("mention_roles")
    var mentionedRoles: ArrayList<String> = ArrayList()

    @Expose
    @SerializedName("attachments")
    var attachments: ArrayList<Attachment> = ArrayList()

    @Expose
    @SerializedName("embeds")
    var embeds: ArrayList<Embed> = ArrayList()


    open fun mentionsMe(): Boolean
    {
        return this.mentionedUsers.find { ConnectionController.isMe(it) } != null
    }
}