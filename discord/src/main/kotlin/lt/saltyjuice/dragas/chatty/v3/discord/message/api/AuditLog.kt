package lt.saltyjuice.dragas.chatty.v3.discord.message.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.AuditLogEntry
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.WebHook

/**
 * Whenever an admin action is performed on the API,
 * an entry is added to the respective guild's audit log.
 * You can specify the reason by attaching the `X-Audit-Log-Reason` request header.
 * This header supports url encoded utf8 characters.
 */
open class AuditLog
{
    /**
     * 	list of webhooks found in the audit log
     */
    @Expose
    @SerializedName("webhooks")
    var webhooks: ArrayList<WebHook> = ArrayList()

    /**
     * list of users found in the audit log
     */
    @Expose
    @SerializedName("users")
    var users: ArrayList<User> = ArrayList()


    /**
     * 	list of audit log entires
     */
    @Expose
    @SerializedName("audit_log_entries")
    var logEntries: ArrayList<AuditLogEntry> = ArrayList()
}