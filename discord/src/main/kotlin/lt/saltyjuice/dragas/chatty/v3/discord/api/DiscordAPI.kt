package lt.saltyjuice.dragas.chatty.v3.discord.api

import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.Parameter
import lt.saltyjuice.dragas.chatty.v3.discord.message.api.AuditLog
import lt.saltyjuice.dragas.chatty.v3.discord.message.api.Invite
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventChannelDelete
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Channel
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Embed
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit
import retrofit2.Call
import retrofit2.http.*
import java.io.File

/**
 * An interface for Retrofit to generate calls for DiscordAPI.
 *
 * Note: For any maps, you should use [Parameter] for keys.
 */
interface DiscordAPI
{
    @GET("gateway/bot")
    fun gatewayInit(): Call<GatewayInit>

    /**
     * Returns an invite object for the given code.
     */
    @GET("invites/{invite-code}")
    fun getInvite(@Path("invite-code") inviteCode: String): Call<Invite>

    /**
     * Delete an invite. Requires the MANAGE_CHANNELS permission. Returns an [Invite] object on success.
     */
    @PATCH("invites/{invite-code}")
    fun deleteInvite(@Path("invite-code") inviteCode: String): Call<Invite>

    /**
     * Accept an invite. This requires the guilds.join OAuth2 scope to be able to accept invites
     * on behalf of normal users (via an OAuth2 Bearer token).
     * Bot users are disallowed. Returns an [Invite] object on success.
     */
    @POST("invites/{invite-code}")
    fun acceptInvite(@Path("invite-code") inviteCode: String): Call<Invite>

    /**
     * Returns an [AuditLog] object for the guild. Requires the 'VIEW_AUDIT_LOG' permission.
     */
    @GET("guilds/{guild-id}/audit-logs")
    fun getGuildAuditLog(@Path("guild-id") guildId: String, @QueryMap map: Map<String, String>): Call<AuditLog>

    /**
     * Returns an [AuditLog] object for the guild. Requires the 'VIEW_AUDIT_LOG' permission.
     */
    @GET("guilds/{guild-id}/audit-logs")
    fun getGuildAuditLog(@Path("guild-id") guildId: String): Call<AuditLog>

    /**
     * Get a channel by ID. Returns a guild channel or dm channel object.
     */
    @GET("channels/{channel-id}")
    fun getChannel(@Path("channel-id") channelId: String): Call<Channel>

    /**
     * Update a channels settings. Requires the 'MANAGE_CHANNELS' permission for the guild. Returns a guild channel
     * on success, and a 400 BAD REQUEST on invalid parameters.
     * Fires a Channel Update Gateway event.
     * All the JSON Params are optional.
     */
    @PATCH("channels/{channel-id}")
    @FormUrlEncoded
    fun modifyChannelPatch(@Path("channel-id") channelId: String, @FieldMap map: Map<String, String>): Call<Channel>

    /**
     * Update a channels settings. Requires the 'MANAGE_CHANNELS' permission for the guild. Returns a guild channel
     * on success, and a 400 BAD REQUEST on invalid parameters.
     *
     * Fires a Channel Update Gateway event.
     */
    @PUT("channels/{channel-id}")
    @FormUrlEncoded
    fun modifyChannelPut(@Path("channel-id") channelId: String, @FieldMap map: Map<String, String>): Call<Channel>

    /**
     *  Delete a guild channel, or close a private message. Requires the 'MANAGE_CHANNELS' permission for the guild.
     *  Returns a guild channel or dm channel object on success.
     *  Fires a [EventChannelDelete] Gateway event.
     *
     *  Deleting a guild channel cannot be undone. Use this with caution, as it is impossible to
     *  undo this action when performed on a guild channel.
     *  In contrast, when used with a private message, it is possible to undo the action
     *  by opening a private message with the recipient again.
     */

    @DELETE("channels/{channel-id}")
    fun deleteChannel(@Path("channel-id") channelId: String): Call<Channel>

    /**
     * Returns the messages for a channel. If operating on a guild channel,
     * this endpoint requires the 'READ_MESSAGES' permission to be present
     * on the current user. Returns an array of message objects on success.
     */
    @GET("channels/{channel-id}/messages")
    fun getChannelMessages(@Path("channel-id") channelId: String): Call<ArrayList<Message>>

    /**
     * Returns the messages for a channel. If operating on a guild channel,
     * this endpoint requires the 'READ_MESSAGES' permission to be present
     * on the current user. Returns an array of message objects on success.
     *
     * The [Parameter.BEFORE], [Parameter.AFTER], and [Parameter.AROUND] keys are mutually exclusive, only one may be passed at a time.
     */
    @GET("channels/{channel-id}/messages")
    fun getChannelMessages(@Path("channel-id") channelId: String, @QueryMap map: Map<String, String>): Call<ArrayList<Message>>


    /**
     * Returns a specific message in the channel. If operating on a guild channel, this endpoints requires
     * the 'READ_MESSAGE_HISTORY' permission to be present on the current user. Returns a message object on success.
     */
    @GET("channels/{channel-id}/messages/{message-id}")
    fun getChannelMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String): Call<Message>

    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    @Multipart
    fun createMessage(@Path("channel-id") channelId: String, @Part("content") message: String, @Part("file") file: File, @PartMap map: Map<String, String>): Call<Message>

    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    @FormUrlEncoded
    fun createMessage(@Path("channel-id") channelId: String, @Field("content") message: String, @Field("embed") embed: Embed, @FieldMap map: Map<String, String>): Call<Message>

    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    @FormUrlEncoded
    fun createMessage(@Path("channel-id") channelId: String, @Field("content") message: String, @FieldMap map: Map<String, String>): Call<Message>


    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    @FormUrlEncoded
    fun createMessage(@Path("channel-id") channelId: String, @Field("content") message: String): Call<Message>

}