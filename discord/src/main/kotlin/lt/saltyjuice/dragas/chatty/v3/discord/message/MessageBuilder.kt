package lt.saltyjuice.dragas.chatty.v3.discord.message

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.Settings
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.exception.MessageBuilderException
import lt.saltyjuice.dragas.chatty.v3.discord.main.DiscordEndpoint
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*

/**
 * Message Builder for discord messages.
 *
 * Contains various helpers and builder methods to easily build a message of various flavors. All methods return this embed builder.
 */
open class MessageBuilder @JvmOverloads constructor(channelId: String = "", typingCallback: Callback<Any> = DiscordEndpoint.emptyCallback) : Callback<Message>
{
    @Expose
    @SerializedName("embed")
    private var embed: Embed? = null

    @Expose
    @SerializedName("content")
    private var content: String = ""

    @Expose
    @SerializedName("attachment")
    private var attachment: File? = null

    private var queryParamas: MutableMap<String, String> = mutableMapOf()

    private var messageBuilder: StringBuilder = StringBuilder()

    private var isBuildingMention: Boolean = false

    private var embedLength = 0

    init
    {
        if (channelId != "")
            DiscordEndpoint.startTyping(channelId, typingCallback)
    }

    /**
     * Builds the message and sends it to some channel. Be it a DM or a public one.
     */
    @JvmOverloads
    @Throws(MessageBuilderException::class)
    open fun send(channelId: String, callback: Callback<Message> = this)
    {
        buildMessage()
        if (attachment == null)
            Utility.discordAPI.createMessage(channelId, this, queryParamas).enqueue(callback)
        else
            Utility.discordAPI.createMessage(channelId, content, attachment!!, queryParamas).enqueue(callback)
    }

    /**
     * Appends or clears the current embed object.
     * @param embed prebuilt embed object
     * @return this
     */
    @JvmOverloads
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun embed(embed: Embed? = null): MessageBuilder
    {
        if (this.embed == null)
            evaluateEmbedLength(embed)
        this.embed = embed
        return this
    }

    /**
     * Adds an author to message. If embed object is unavailable, it's created.
     * @param author prebuilt author object
     * @return this
     */
    @JvmOverloads
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun author(author: Author? = null): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        decreaseEmbedCounter(embed.author)
        embed.author = author
        increaseEmbedCounter(embed.author)
        return embed(embed)
    }

    /**
     * Adds an author to message. If embed or author objects are unavailable, they're created.
     * @param author prebuilt author object
     * @return this
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun author(author: String): MessageBuilder
    {
        val authorInternal = this.embed?.author ?: Author()
        authorInternal.name = author
        return author(authorInternal)
    }

    /**
     * Sets message to this builder and calls [validate].
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun message(message: String): MessageBuilder
    {
        this.content = message
        validate()
        return this
    }

    /**
     * Calls `toString` on internal string builder.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun buildMessage(): MessageBuilder
    {
        if (isBuildingMention)
            throw MessageBuilderException("You haven't finished mentioning someone.")
        //validate()
        return message(messageBuilder.toString())
    }

    /**
     * Mentions user.
     */
    @Synchronized
    @JvmOverloads
    @Throws(MessageBuilderException::class)
    fun mention(user: User, shouldUseNickname: Boolean = false): MessageBuilder
    {
        val mentionType = if (shouldUseNickname) MentionType.USER_NICKNAME else MentionType.USER
        return mentionId(user.id, mentionType)
    }

    /**
     * Mentions channel.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mention(channel: Channel): MessageBuilder
    {
        return mentionId(channel.id, MentionType.CHANNEL)
    }

    /**
     * Mentions role.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mention(role: Role): MessageBuilder
    {
        if (!role.isMentionable)
            throw MessageBuilderException("Role can't be mentioned")
        return mentionId(role.id, MentionType.ROLE)
    }

    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mention(emoji: Emoji): MessageBuilder
    {
        return mentionStart().append(":${emoji.name}:${emoji.id}").mentionEnd()
    }

    /**
     * Starts a mention.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mentionStart(): MessageBuilder
    {
        if (isBuildingMention)
            throw MessageBuilderException("Mention is already being built. $messageBuilder")
        isBuildingMention = true
        return append("<")
    }

    /**
     * Begins mentioning something. When mention ends, you should call [mentionEnd]
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mentionStart(mentionType: MentionType): MessageBuilder
    {
        return mentionStart().append(mentionType.value)
    }

    /**
     * Mentions particular ID.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mentionId(id: String, mentionType: MentionType): MessageBuilder
    {
        return mentionStart(mentionType).append(id).mentionEnd()
    }

    /**
     * Ends mention building.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun mentionEnd(): MessageBuilder
    {
        if (!isBuildingMention)
            throw MessageBuilderException("Mention isn't even being built. $messageBuilder")
        isBuildingMention = false
        return append(">")
    }

    /**
     * Appends some text.
     *
     * @throws MessageBuilderException when appendable text length and current text length sum is over limit.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun append(text: String): MessageBuilder
    {
        if (text.contains("\n") && isBuildingMention)
            throw MessageBuilderException("You can't append lines while building mentions.")
        if (messageBuilder.length + text.length < Settings.MAX_MESSAGE_CONTENT_LENGTH)
            messageBuilder.append(text)
        else
            throw MessageBuilderException("Content length over limit (${messageBuilder.length + text.length} > ${Settings.MAX_MESSAGE_CONTENT_LENGTH}. Consider using an embed instead.")
        return this
    }

    /**
     * Appends message and then appends \n terminator to start a new line.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun appendLine(text: String): MessageBuilder
    {
        return append("$text\n")
    }

    /**
     * Begins code snippet. Style can be anything, usually it matches some language name: Markdown, Java, Ruby, etc.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun beginCodeSnippet(style: String): MessageBuilder
    {
        return appendLine("```$style")
    }

    @Synchronized
    @Throws(MessageBuilderException::class)
    fun endCodeSnippet(): MessageBuilder
    {
        return appendLine("\n```")
    }

    /**
     * Validates the message and throws if anything is out of order.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun validate()
    {
        if (isBuildingMention)
            throw MessageBuilderException("Mention is being built.")
        if (content.length > Settings.MAX_MESSAGE_CONTENT_LENGTH)
            throw MessageBuilderException("Content is over maximum length! Expected: ${Settings.MAX_MESSAGE_CONTENT_LENGTH}, got: ${content.length}")
        if (embedLength > Settings.MAX_EMBED_CONTENT_LENGTH)
            throw MessageBuilderException("Embed is over maximum length! Expected: ${Settings.MAX_EMBED_CONTENT_LENGTH}, got: $embedLength")
        validate("(@[!&]?|#|:(\\w+):)\\d+", false)
        validate("(@[!&]?|#|:(\\w+):)\\d+", true)

    }

    /**
     * Appends description to this embed.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun description(description: String): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        decreaseEmbedCounter(embed.description)
        embed.description = description
        increaseEmbedCounter(embed.description)
        return embed(embed)
        //return this
    }

    /**
     * Sets color for this embed.
     */
    @Synchronized
    fun color(color: Int): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.color = color
        return embed(embed)
    }

    /**
     * Sets title for this embed.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun title(title: String): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        decreaseEmbedCounter(embed.title)
        embed.title = title
        increaseEmbedCounter(embed.title)
        return embed(embed)
    }

    /**
     * Appends field for this embed.
     */
    @JvmOverloads
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun field(name: String, value: String, inline: Boolean = false): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        if (embed.fields.size < Settings.FIELD_LIMIT)
        {
            val field = Field(name, value, inline)
            increaseEmbedCounter(field)
            embed.fields.add(field)
        }
        else
            throw MessageBuilderException("Too many embed fields!")
        return embed(embed)
    }

    /**
     * Appends image to this embed
     */
    @Synchronized
    fun image(url: String): MessageBuilder
    {
        val image = embed?.image ?: Image()
        image.url = url
        return image(image)
        //return embed(embed)
    }

    /**
     * Appends image to this embed.
     */
    @Synchronized
    fun image(image: Image): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.image = image
        return embed(embed)
    }

    /**
     * Appends footer to this embed.
     */
    @Synchronized
    fun footer(footer: Footer): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        decreaseEmbedCounter(embed.footer)
        embed.footer = footer
        increaseEmbedCounter(embed.footer)
        return embed(embed)
    }

    /**
     * Appends footer to this embed.
     */
    @Synchronized
    fun footer(text: String): MessageBuilder
    {
        val footer = embed?.footer ?: Footer()
        footer.text = text
        return footer(footer)
    }

    /**
     * Appends timestamp
     */
    @Synchronized
    fun timestamp(time: Long): MessageBuilder
    {
        return timestamp(Date(time))
    }

    /**
     * Appends timestamp
     */
    @Synchronized
    fun timestamp(time: Date): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.timestamp = time
        return embed(embed)
    }

    /**
     * Appends thumbnail.
     */
    @Synchronized
    fun thumbnail(thumbnail: Thumbnail): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.thumbnail = thumbnail
        return embed(embed)
    }

    /**
     * Appends thumbnail.
     */
    @Synchronized
    fun thumbnail(url: String): MessageBuilder
    {
        val thumbnail = embed?.thumbnail ?: Thumbnail()
        thumbnail.url = url
        return thumbnail(thumbnail)
    }

    /**
     * Appends video.
     */
    @Synchronized
    fun video(video: Video): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.video = video
        return embed(embed)
    }

    /**
     * Appends video.
     */
    @Synchronized
    fun video(url: String): MessageBuilder
    {
        val video = embed?.video ?: Video()
        video.url = url
        return video(video)
    }

    /**
     * Appends attachment.
     */
    @Synchronized
    fun attachment(file: File): MessageBuilder
    {
        attachment = file
        return this
    }

    /**
     * Validates the message for mentions that may be built incorrectly.
     */
    @Synchronized
    @Throws(MessageBuilderException::class)
    private fun validate(regex: String, starting: Boolean)
    {
        val characterToAppend = if (starting) "<" else ">"
        val characterToLookFor = if (starting) ">" else "<"
        val actualRegex = if (starting) characterToAppend + regex else regex + characterToAppend
        val incorrectRegex = Regex(actualRegex)
        val message = messageBuilder.toString()
        incorrectRegex.findAll(message).forEach()
        {
            val index = if (starting) it.range.last + 1 else it.range.first - 1
            if (message.getOrNull(index)?.toString() != characterToLookFor)
            {
                throw MessageBuilderException("Incorrectly built mention at index ${index + 1}. Missing required character: $characterToLookFor: \n $message \n ${"^here".padStart(index)}")
            }
        }
    }

    private fun decreaseEmbedCounter(value: String): Int
    {

        if (embedLength - value.length < 0)
            throw MessageBuilderException("Invalid embed counter value: $embedLength")
        embedLength -= value.length
        return value.length
    }

    private fun increaseEmbedCounter(value: String): Int
    {
        if (embedLength + value.length > Settings.MAX_EMBED_CONTENT_LENGTH)
            throw MessageBuilderException("Invalid embed counter value: $embedLength")
        embedLength += value.length
        return value.length
    }

    private fun decreaseEmbedCounter(author: Author?): Int
    {
        author ?: return 0
        return decreaseEmbedCounter(author.name)
    }

    private fun increaseEmbedCounter(author: Author?): Int
    {
        author ?: return 0
        return increaseEmbedCounter(author.name)
    }

    private fun increaseEmbedCounter(field: Field?): Int
    {
        field ?: return 0
        return increaseEmbedCounter(field.value) + increaseEmbedCounter(field.name)
    }

    private fun increaseEmbedCounter(footer: Footer?): Int
    {
        footer ?: return 0
        return increaseEmbedCounter(footer.text)
    }

    private fun decreaseEmbedCounter(footer: Footer?): Int
    {
        footer ?: return 0
        return decreaseEmbedCounter(footer.text)
    }

    private fun evaluateEmbedLength(embed: Embed?)
    {
        if (embed == null)
        {
            embedLength = 0
            return
        }
        else
        {
            if (embed.provider != null)
                throw MessageBuilderException("Invalid embed! You cannot set provider field.")
            val oldLength = embedLength
            embedLength = 0
            embed.fields.forEach { increaseEmbedCounter(it) }
            increaseEmbedCounter(embed.author)
            increaseEmbedCounter(embed.title)
            increaseEmbedCounter(embed.description)
            increaseEmbedCounter(embed.footer)
            if (embedLength > Settings.MAX_EMBED_CONTENT_LENGTH)
            {
                val message = "Embed is over maximum length! Expected: ${Settings.MAX_EMBED_CONTENT_LENGTH}, got: $embedLength"
                embedLength = oldLength
                throw MessageBuilderException(message)
            }
        }
    }


    @Synchronized
    private fun getEmbedOrCreate() = this.embed ?: Embed()

    override fun onResponse(call: Call<Message>, response: Response<Message>)
    {

    }

    override fun onFailure(call: Call<Message>, t: Throwable)
    {
        t.printStackTrace(System.err)
    }

    enum class MentionType(val value: String)
    {
        USER("@"),
        USER_NICKNAME("@!"),
        CHANNEL("#"),
        ROLE("@&"),
    }
}