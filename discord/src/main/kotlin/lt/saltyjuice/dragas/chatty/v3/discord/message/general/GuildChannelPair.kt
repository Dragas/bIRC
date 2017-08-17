package lt.saltyjuice.dragas.chatty.v3.discord.message.general

import com.google.gson.annotations.SerializedName

open class GuildChannelPair
{
    @SerializedName("guild_id")
    var guildId: String = ""

    @SerializedName("channel_id")
    var channelId: String = ""
}