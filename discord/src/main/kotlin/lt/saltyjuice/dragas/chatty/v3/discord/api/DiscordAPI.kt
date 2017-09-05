package lt.saltyjuice.dragas.chatty.v3.discord.api

import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.Parameter
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.api.AuditLog
import lt.saltyjuice.dragas.chatty.v3.discord.message.api.Invite
import lt.saltyjuice.dragas.chatty.v3.discord.message.api.InviteBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.api.InviteMetadata
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventChannelDelete
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Channel
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Embed
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.ChannelBuilder
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
     *
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    fun createMessage(@Path("channel-id") channelId: String, @Body message: MessageBuilder, @QueryMap map: Map<String, String>): Call<Message>

    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     *
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    fun createMessage(@Path("channel-id") channelId: String, @Body message: MessageBuilder): Call<Message>

    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     *
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    @FormUrlEncoded
    fun createMessage(@Path("channel-id") channelId: String, @Field("content") message: String, @FieldMap map: Map<String, String>): Call<Message>


    /**
     * Post a message to a guild text or DM channel. If operating on a guild channel, this endpoint requires the
     * 'SEND_MESSAGES' permission to be present on the current user.
     *
     * Returns a message object. Fires a Message Create Gateway event. See [message formatting](https://discordapp.com/developers/docs/reference#message-formatting) for more information
     * on how to properly format messages.
     */
    @POST("channels/{channel-id}/messages")
    @FormUrlEncoded
    fun createMessage(@Path("channel-id") channelId: String, @Field("content") message: String): Call<Message>

    /**
     * Create a reaction for the message. This endpoint requires the 'READ_MESSAGE_HISTORY' permission to be present
     * on the current user. Additionally, if nobody else has reacted to the message using this emoji,
     * this endpoint requires the 'ADD_REACTIONS' permission to be present on the current user.
     * Returns a 204 empty response on success.
     */
    @PUT("channels/{channel-id}/messages/{message-id}/reactions/{emoji}/@me")
    fun createReaction(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Path("emoji") emojiId: String): Call<Any>

    /**
     * Delete a reaction the current user has made for the message. Returns a 204 empty response on success.
     */
    @DELETE("channels/{channel-id}/messages/{message-id}/reactions/{emoji}/@me")
    fun deleteOwnReaction(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Path("emoji") emojiId: String): Call<Any>

    /**
     * Deletes another user's reaction. This endpoint requires the 'MANAGE_MESSAGES' permission to be
     * present on the current user. Returns a 204 empty response on success.
     */
    @DELETE("channels/{channel-id}/messages/{message-id}/reactions/{emoji}/{user-id}")
    fun deleteUserReaction(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Path("emoji") emojiId: String, @Path("user-id") userId: String): Call<Any>

    /**
     * Get a list of users that reacted with this emoji. Returns an array of [User] objects on success.
     */
    @GET("channels/{channel-id}/messages/{message-id}/reactions/{emoji}")
    fun getUsersForReaction(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Path("emoji") emojiId: String): Call<ArrayList<User>>

    /**
     * Deletes all reactions on a message. This endpoint requires the 'MANAGE_MESSAGES'
     * permission to be present on the current user.
     */
    @DELETE("channels/{channel-id}/messages/{message-id}/reactions")
    fun deleteAllReactions(@Path("channel-id") channelId: String, @Path("message-id") messageId: String): Call<Any>

    /**
     * Edit a previously sent message. You can only edit messages that have been sent by the current user.
     * Returns a message object. Fires a Message Update Gateway event.
     */
    @PATCH("channels/{channel-id}/messages/{message-id}")
    @FormUrlEncoded
    fun editMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Field("content") content: String): Call<Message>

    /**
     * Edit a previously sent message. You can only edit messages that have been sent by the current user.
     * Returns a message object. Fires a Message Update Gateway event.
     */
    @PATCH("channels/{channel-id}/messages/{message-id}")
    @FormUrlEncoded
    fun editMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Field("embed") embed: Embed): Call<Message>

    /**
     * Edit a previously sent message. You can only edit messages that have been sent by the current user.
     * Returns a message object. Fires a Message Update Gateway event.
     */
    @PATCH("channels/{channel-id}/messages/{message-id}")
    @FormUrlEncoded
    fun editMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String, @Field("content") content: String, @Field("embed") embed: Embed): Call<Message>


    /**
     * Delete a message. If operating on a guild channel and trying to delete a message that
     * was not sent by the current user, this endpoint requires the 'MANAGE_MESSAGES' permission.
     * Returns a 204 empty response on success. Fires a Message Delete Gateway event.
     */
    @DELETE("channels/{channel-id}/messages/{message-id}")
    fun deleteMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String): Call<Any>

    /**
     *Delete multiple messages in a single request.
     * This endpoint can only be used on guild channels and requires the 'MANAGE_MESSAGES' permission.
     * Returns a 204 empty response on success. Fires multiple Message Delete Gateway events.
     *
     * The gateway will ignore any individual messages that do not exist or do not belong to this channel,
     * but these will count towards the minimum and maximum message count.
     * Duplicate snowflakes will only be counted once for these limits.
     *
     * This endpoint will not delete messages older than 2 weeks, and will fail if
     * any message provided is older than that. An endpoint will be added in the
     * future to prune messages older than 2 weeks from a channel.
     */
    @POST("channels/{channel-id}/message/bulk-delete")
    @FormUrlEncoded
    fun deleteMessageBulk(@Path("channel-id") channelId: String, @Field("messages") vararg messageId: String): Call<Any>

    /**
     * Edit the channel permission overwrites for a user or role in a channel.
     * Only usable for guild channels. Requires the 'MANAGE_ROLES' permission.
     * Returns a 204 empty response on success. For more information about permissions, see
     * [permissions](https://discordapp.com/developers/docs/topics/permissions#permissions).
     *
     * Params
     * * allow  integer     the bitwise value of all allowed permissions
     * * deny   integer     the bitwise value of all disallowed permissions
     * * type   string      "member" for a user or "role" for a role
     */
    @PUT("channels/{channel-id}/permissions/{overwrite-id}")
    @FormUrlEncoded
    fun editChannelPermissions(@Path("channel-id") channelId: String, @Path("overwrite-id") overwriteId: String, @FieldMap params: Map<String, String>): Call<Any>

    /**
     * Returns a list of [Invite] objects (with [InviteMetadata]) for the channel.
     * Only usable for guild channels. Requires the 'MANAGE_CHANNELS' permission.
     */
    @GET("channels/{channel-id}/invites")
    fun getChannelInvites(@Path("channel-id") channelId: String): Call<ArrayList<Invite>>

    /**
     * Create a new invite object for the channel. Only usable for guild channels.
     * Requires the CREATE_INSTANT_INVITE permission.
     *
     * All JSON paramaters for this route are optional, however the request body is not.
     * If you are not sending any fields, you still have to send an empty JSON object ({}).
     * Returns an [Invite] object.
     */
    @POST("channels/{channel-id}/invites")
    fun createChannelInvite(@Path("channel-id") channelId: String, @Body creatableInvite: InviteBuilder): Call<Invite>

    /**
     * Delete a channel permission overwrite for a user or role in a channel.
     * Only usable for guild channels.
     *
     * Requires the 'MANAGE_ROLES' permission.
     *
     * Returns a 204 empty response on success. For more information about permissions, see permissions
     */
    @DELETE("channel/{channel-id}/permissions/{overwrite-id}")
    fun deleteChannelPermission(@Path("channel-id") channelId: String, @Path("overwrite-id") overwriteId: String): Call<Any>

    /**
     * Post a typing indicator for the specified channel. Generally bots should not implement this route.
     * However, if a bot is responding to a command and expects the computation to take a few seconds,
     * this endpoint may be called to let the user know that the bot is processing their message.
     *
     * Returns a 204 empty response on success. Fires a Typing Start Gateway event.
     */
    @POST("channels/{channel-id}/typing")
    fun triggerTypingIndicator(@Path("channel-id") channelId: String): Call<Any>

    /**
     * Returns all pinned messages in the channel as an array of [Message] objects.
     */
    @GET("channel/{channel-id}/pins")
    fun getPinnedMessages(@Path("channel-id") channelId: String): Call<ArrayList<Message>>

    /**
     * Pin a message in a channel. Requires the 'MANAGE_MESSAGES' permission. Returns a 204 empty response on success.
     */
    @PUT("channels/{channel-id}/pins/{message-id}")
    fun addPinnedMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String): Call<Any>

    /**
     * Deletes a message in a channel. Requires the 'MANAGE_MESSAGES' permission. Returns a 204 empty response on success.
     */
    @DELETE("channels/{channel-id}/pins/{message-id}")
    fun deletePinnedMessage(@Path("channel-id") channelId: String, @Path("message-id") messageId: String): Call<Any>


    /**
     * Adds a recipient to a Group DM using their access token
     *
     * @param accessToken access token of a user that has granted your app the gdm.join scope
     * @param nickname nickname of the user being added
     */
    @PUT("channels/{channel-id}/recipients/{user-id}")
    @FormUrlEncoded
    fun addGroupDMRecipient(@Path("channel-id") channelId: String, @Path("user-id") userId: String, @Field("access_token") accessToken: String, @Field("nick") nickname: String): Call<Any>

    /**
     * Removes a recipient from a Group DM
     */
    @DELETE("channels/{channel-id}/recipients/{user-id}")
    fun removeGroupDMRecipient(@Path("channel-id") channelId: String, @Path("user-id") userId: String): Call<Any>

    /**
     * Returns a user object for a given user ID.
     */
    @GET("users/{user-id}")
    fun getUser(@Path("user-id") userId: String): Call<User>


    /**
     * Returns the user object of the requester's account. For OAuth2, this requires the identify scope,
     * which will return the object without an email, and optionally the email scope,
     * which returns the object with an email.
     */
    @GET("users/@me")
    fun getUser(): Call<User>

    /**
     * Create a new DM channel with a user. Returns a DM channel object.
     */
    @POST("users/@me/channels")
    fun createChannel(@Body channelBuilder: ChannelBuilder): Call<Channel>
}