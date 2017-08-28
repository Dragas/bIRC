package lt.saltyjuice.dragas.chatty.v3.discord.message.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Channel
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Guild

/**
 * Represents a code that when used, adds a user to a guild.
 */
open class Invite
{
    /**
     * 	the invite code (unique ID)
     */
    @Expose
    @SerializedName("code")
    var code: String = ""

    /**
     * the guild this invite is for. Note: Partial.
     */
    @Expose
    @SerializedName("guild")
    var guild: Guild = Guild()

    /**
     * 	the channel this invite is for. Note: Partial.
     */
    @Expose
    @SerializedName("channel")
    var channel: Channel = Channel()


    /**
     * probably holds the reference to this invites metadata
     */
    @Expose
    @SerializedName("metadata")
    var metadata: InviteMetadata? = null
}