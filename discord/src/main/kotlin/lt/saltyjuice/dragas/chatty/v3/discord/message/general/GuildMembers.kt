package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Used to request offline members for a guild. When initially connecting,
 * the gateway will only send offline members if a guild has less than the large_threshold
 * members (value in the Gateway Identify). If a client wishes to receive additional members,
 * they need to explicitly request them via this operation. The server will send Guild Members
 * Chunk events in response with up to 1000 members per chunk until all members that match
 * the request have been sent.
 */
open class GuildMembers
{
    @Expose
    @SerializedName("guild_id")
    var guildId: String = "0" // snowflake
    @Expose
    @SerializedName("query")
    var query: String = ""
    @Expose
    @SerializedName("limit")
    var limit: Int = 0
}