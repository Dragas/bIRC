package lt.saltyjuice.dragas.chatty.v3.discord.enumerated

enum class Parameter private constructor(private val value: String)
{
    /**
     * When specified, filters for particular user ID
     */
    USER_ID("user_id"),

    /**
     * When specified, filters for particular audit log event type.
     */
    ACTION_TYPE("action_type"),

    /**
     * When specified, filters for entries before the specified entry id.
     * May be applied to log entries, messages.
     */
    BEFORE("before"),

    /**
     * Notes how many entries should be returned (when unspecified, defaults to 50).
     * Can be between 1 and 100
     */
    LIMIT("limit"),

    /**
     * Name between 2 and 100 characters.
     */
    NAME("name"),

    /**
     * Position in the left hand listing. Should prefer an integer.
     */
    POSITION("position"),

    /**
     * Channel's topic between 0 and 1024 symbols.
     */
    TOPIC("topic"),

    /**
     * Voice channel's bitrate. An Integer between 8000 and 96000 (all the way up to 128000 for VIP servers)
     */
    BITRATE("bitrate"),

    /**
     * Refers to user limit of the voice channel. Can be between 0 and 99. 0 Implies that there is no limit.
     */
    USER_LIMIT("user_limit"),

    /**
     * Gets messages around this message ID.
     */
    AROUND("around"),

    /**
     * Gets messages after this ID
     */
    AFTER("after"),

    /**
     * a nonce that can be used for optimistic message sending
     */
    NONCE("nonce"),

    /**
     * true if this is text to speech message
     */
    TTS("tts"),

    /**
     * embedded rich content, should contain an embedded object
     *
     * Can't be used in multipart message calls.
     */
    EMBED("embed")

}