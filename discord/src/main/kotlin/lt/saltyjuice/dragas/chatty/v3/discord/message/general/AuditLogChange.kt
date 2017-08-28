package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.AuditLogChangeKey

open class AuditLogChange<T>
{
    /**
     * 	new value of the key
     */
    @Expose
    @SerializedName("old_value")
    var oldValue: T? = null

    /**
     * old value of the key
     */
    @Expose
    @SerializedName("new_value")
    var newValue: T? = null

    /**
     * 	type of audit log change key
     */
    @Expose
    @SerializedName("key")
    var key: AuditLogChangeKey = AuditLogChangeKey.INVALID
}