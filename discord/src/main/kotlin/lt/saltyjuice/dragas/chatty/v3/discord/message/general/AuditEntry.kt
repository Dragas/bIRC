package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.AuditLogEvent

/**
 * All fields here are optional and are only available depending per audit log event type.
 */
open class AuditEntry
{
    /**
     * number of days after which inactive members were kicked
     *
     * Available when audit type is [AuditLogEvent.MEMBER_PRUNE]
     */
    @Expose
    @SerializedName("delete_member_days")
    var inactivityTime: String? = null

    /**
     * number of members removed by the prune
     *
     * Available when audit type is [AuditLogEvent.MEMBER_PRUNE]
     */
    @Expose
    @SerializedName("members_removed")
    var membersRemoved: String? = null

    /**
     * channel in which the messages were deleted
     *
     * Available when audit type is [AuditLogEvent.MESSAGE_DELETE]
     */
    @Expose
    @SerializedName("channel_id")
    var channelId: String? = null

    /**
     * number of deleted messages
     *
     * Available when audit type is [AuditLogEvent.MESSAGE_DELETE]
     */
    @Expose
    @SerializedName("count")
    var count: String? = ""

    /**
     * type of overwritten entity
     *
     * available when audit type is [AuditLogEvent.CHANNEL_OVERWRITE_CREATE] or [AuditLogEvent.CHANNEL_OVERWRITE_UPDATE]
     * or [AuditLogEvent.CHANNEL_OVERWRITE_DELETE]
     */
    @Expose
    @SerializedName("type")
    var type: Overwrite? = null

    /**
     * id of overwritten entity
     *
     * available when audit type is [AuditLogEvent.CHANNEL_OVERWRITE_CREATE] or [AuditLogEvent.CHANNEL_OVERWRITE_UPDATE]
     * or [AuditLogEvent.CHANNEL_OVERWRITE_DELETE]
     */
    @Expose
    @SerializedName("id")
    var id: String? = null

    /**
     * name of the role if the type is role
     *
     * available when audit type is [AuditLogEvent.CHANNEL_OVERWRITE_CREATE] or [AuditLogEvent.CHANNEL_OVERWRITE_UPDATE]
     * or [AuditLogEvent.CHANNEL_OVERWRITE_DELETE]
     */

    @Expose
    @SerializedName("role_name")
    var name: String? = null
}