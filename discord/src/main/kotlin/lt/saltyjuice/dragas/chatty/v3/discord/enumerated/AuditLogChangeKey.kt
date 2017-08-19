package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class AuditLogChangeKey private constructor(private val value: String)
{
    INVALID("INVALID"),
    /**
     * icon changed
     * affects: guild
     * type: string
     */
    ICON_HASH("icon_hash"),

    /**
     * invite splash page artwork changed
     * affects: guild
     * type: string
     */
    SPLASH_HASH("splash_hash"),

    /**
     * owner changed
     * affects: guild
     * type: snowflake
     */
    OWNER_ID("owner_id"),

    /**
     * region changed
     * affects: guild
     * type: string
     */
    REGION("region"),

    /**
     * afk channel changed
     * affects: guild
     * type: snowflake
     */
    AFK_CHANNEL_ID("afk_channel_id"),

    /**
     * afk timout duration changed
     * affects: guild
     * type: integer
     */
    AFK_TIMEOUT("afk_timeout"),

    /**
     * two-factor auth requirement changed
     * affects: guild
     * type: integer
     */
    MFA_LEVEL("mfa_level"),

    /**
     * required verification level changed
     * affects: guild
     * type: integer
     */
    VERIFICATION_LEVEL("verification_level"),

    /**
     * change in whose messages are scanned and deleted for explicit content in the server
     * affects: guild
     * type: integer
     */
    EXPLICIT_CONTENT_FILTER("explicit_content_filter"),

    /**
     * default message notification level changed
     * affects: guild
     * type: integer
     */
    DEFAULT_MESSAGE_NOTIFICATIONS("default_message_notifications"),

    /**
     * guild invite vanity url changed
     * affects: guild
     * type: string
     */
    VANITY_URL_CODE("vanity_url_code"),

    /**
     * new role added
     * affects: guild
     * type: array of role objects
     */
    ADD("\$add"),

    /**
     * role removed
     * affects: guild
     * type: array of role objects
     */
    REMOVE("\$remove"),

    /**
     * change in number of days after which inactive and role-unassigned members are kicked
     * affects: guild
     * type: integer
     */
    PRUNE_DELETE_DAYS("prune_delete_days"),

    /**
     * server widget enabled/disable
     * affects: guild
     * type: bool
     */
    WIDGET_ENABLED("widget_enabled"),

    /**
     * channel id of the server widget changed
     * affects: guild
     * type: snowflake
     */
    WIDGET_CHANNEL_ID("widget_channel_id"),

    /**
     * text or voice channel position changed
     * affects: channel
     * type: integer
     */
    POSITION("position"),

    /**
     * text channel topic changed
     * affects: channel
     * type: string
     */
    TOPIC("topic"),

    /**
     * voice channel bitrate changed
     * affects: channel
     * type: integer
     */
    BITRATE("bitrate"),

    /**
     * permissions on a channel changed
     * affects: channel
     * type: array of channel overwrite objects
     */
    PERMISSION_OVERWRITES("permission_overwrites"),

    /**
     * channel nsfw restriction changed
     * affects: channel
     * type: bool
     */
    NSFW("nsfw"),

    /**
     * application id of the added or removed webhook or bot
     * affects: channel
     * type: snowflake
     */
    APPLICATION_ID("application_id"),

    /**
     * permissions for a role changed
     * affects: role
     * type: integer
     */
    PERMISSIONS("permissions"),

    /**
     * role color changed
     * affects: role
     * type: integer
     */
    COLOR("color"),

    /**
     * role is now displayed/no longer displayed separate from online users
     * affects: role
     * type: bool
     */
    HOIST("hoist"),

    /**
     * role is now mentionable/unmentionable
     * affects: role
     * type: bool
     */
    MENTIONABLE("mentionable"),

    /**
     * a permission on a text or voice channel was allowed for a role
     * affects: role
     * type: integer
     */
    ALLOW("allow"),

    /**
     * a permission on a text or voice channel was denied for a role
     * affects: role
     * type: integer
     */
    DENY("deny"),

    /**
     * invite code changed
     * affects: invite
     * type: string
     */
    CODE("code"),

    /**
     * channel for invite code changed
     * affects: invite
     * type: snowflake
     */
    CHANNEL_ID("channel_id"),

    /**
     * person who created invite code changed
     * affects: invite
     * type: snowflake
     */
    INVITER_ID("inviter_id"),

    /**
     * change to max number of times invite code can be used
     * affects: invite
     * type: integer
     */
    MAX_USES("max_uses"),

    /**
     * number of times invite code used changed
     * affects: invite
     * type: integer
     */
    USES("uses"),

    /**
     * how long invite code lasts changed
     * affects: invite
     * type: integer
     */
    MAX_AGE("max_age"),

    /**
     * invite code is temporary/never expires
     * affects: invite
     * type: bool
     */
    TEMPORARY("temporary"),

    /**
     * user server deafened/undeafened
     * affects: user
     * type: bool
     */
    DEAF("deaf"),

    /**
     * user server muted/unmuteds
     * affects: user
     * type: bool
     */
    MUTE("mute"),

    /**
     * user nickname changed
     * affects: user
     * type: string
     */
    NICK("nick"),

    /**
     * user avatar changed
     * affects: user
     * type: string
     */
    AVATAR_HASH("avatar_hash"),

    /**
     * the id of the changed entity - sometimes used in conjunction with other keys
     * affects: any
     * type: snowflake
     */
    ID("id"),

    /**
     * type of entity created
     * affects: any
     * type: integer (channel type) or string
     */
    TYPE("type")
}