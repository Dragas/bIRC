package lt.saltyjuice.dragas.chatty.v3.discord.message.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Get Gateway Bot
 *
 * GET/gateway/bot
 *
 * Returned after initial call to discord api. This is an object with the same information as Get Gateway,
 * plus a shards key, containing the recommended number of shards to connect with (as an integer).
 * Bots that want to dynamically/automatically spawn shard processes should use this endpoint
 * to determine the number of processes to run. This route should be called once when
 * starting up numerous shards, with the response being cached and passed to
 * all sub-shards/processes. Unlike the Get Gateway, this route should
 * not be cached for extended periods of time as the value is not
 * guaranteed to be the same per-call, and changes as the bot
 * joins/leaves guilds.
 *
 * Since this is not a request that should be used later in the application's lifecycle, it should only be used
 * while initializing the bot.
 */
open class GatewayInit
{
    @Expose
    @SerializedName("url")
    var url: String = ""

    @Expose
    @SerializedName("shards")
    var shards: Int = 0
}