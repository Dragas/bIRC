package lt.saltyjuice.dragas.chatty.v3.discord.message.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import java.util.*

open class InviteMetadata
{
    /**
     * 	user who created the invite
     */
    @Expose
    @SerializedName("inviter")
    var inviter: User = User()

    /**
     * 	number of times this invite has been used
     */
    @Expose
    @SerializedName("uses")
    var uses: Int = -1

    /**
     * 	max number of times this invite can be used
     */
    @Expose
    @SerializedName("max_uses")
    var maxUses = -1

    /**
     * whether this invite only grants temporary membership
     */
    @Expose
    @SerializedName("temporary")
    var temporary: Boolean = true

    /**
     * 	duration (in seconds) after which the invite expires
     */
    @Expose
    @SerializedName("max_age")
    var maxAge: Int = -1

    /**
     * 	when this invite was created
     */
    @Expose
    @SerializedName("created_at")
    var date: Date = Date()

    /**
     * 	whether this invite is revoked
     */
    @Expose
    @SerializedName("revoked")
    var revoked: Boolean = false
}