package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.AuditLogEvent


open class AuditLogEntry
{
    /**
     * 	id of the affected entity (webhook, user, role, etc.)
     */
    @Expose
    @SerializedName("target_id")
    var targetId: String = ""

    /**
     * changes made to the target_id
     *
     * Since this can be an object of mixed type
     */
    @Expose
    @SerializedName("changes")
    var changes: ArrayList<AuditLogChange<*>> = ArrayList()

    /**
     * 	the user who made the changes
     */
    @Expose
    @SerializedName("user_id")
    var userId: String = ""

    /**
     * 	id of the entry
     */
    @Expose
    @SerializedName("id")
    var id: String = ""

    /**
     * 	type of action that occured
     */
    @Expose
    @SerializedName("action_type")
    var type: AuditLogEvent = AuditLogEvent.INVALID

    @Expose
    @SerializedName("options")
    var options: ArrayList<AuditEntry> = ArrayList()
}