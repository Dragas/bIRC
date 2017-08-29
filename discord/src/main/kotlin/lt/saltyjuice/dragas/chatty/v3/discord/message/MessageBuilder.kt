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

    init
    {
        if (channelId != "")
            DiscordEndpoint.startTyping(channelId, typingCallback)
    }

    @JvmOverloads
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
    fun embed(embed: Embed? = null): MessageBuilder
    {
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
    fun author(author: Author? = null): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.author = author
        return embed(embed)
    }

    /**
     * Adds an author to message. If embed or author objects are unavailable, they're created.
     * @param author prebuilt author object
     * @return this
     */
    @Synchronized
    fun author(author: String): MessageBuilder
    {
        val authorInternal = this.embed?.author ?: Author()
        authorInternal.name = author
        return author(authorInternal)
    }

    @Synchronized
    fun message(message: String): MessageBuilder
    {
        this.content = message
        return this
    }

    @Synchronized
    @Throws(MessageBuilderException::class)
    fun buildMessage(): MessageBuilder
    {
        if (isBuildingMention)
            throw MessageBuilderException("You haven't finished mentioning someone.")
        validate()
        return message(messageBuilder.toString())
    }

    @Synchronized
    @JvmOverloads
    fun mention(user: User, shouldUseNickname: Boolean = false): MessageBuilder
    {
        val mentionType = if (shouldUseNickname) MentionType.USER_NICKNAME else MentionType.USER
        return mentionId(user.id, mentionType)
    }

    @Synchronized
    fun mention(channel: Channel): MessageBuilder
    {
        return mentionId(channel.id, MentionType.CHANNEL)
    }

    @Synchronized
    fun mention(role: Role): MessageBuilder
    {
        return mentionId(role.id, MentionType.ROLE)
    }

    @Synchronized
    fun mentionStart(mentionType: MentionType): MessageBuilder
    {
        isBuildingMention = true
        return append("<${mentionType.value}")
    }

    @Synchronized
    fun mentionId(id: String, mentionType: MentionType): MessageBuilder
    {
        return mentionStart(mentionType).append(id).mentionEnd()
    }

    @Synchronized
    fun mentionEnd(): MessageBuilder
    {
        isBuildingMention = false
        return append(">")
    }

    @Synchronized
    @Throws(MessageBuilderException::class)
    fun append(text: String): MessageBuilder
    {
        if (messageBuilder.length + text.length < Settings.MAX_MESSAGE_CONTENT_LENGTH)
            messageBuilder.append(text)
        else
            throw MessageBuilderException("Content length over limit. Consider using an embed instead.")
        return this
    }

    @Synchronized
    @Throws(MessageBuilderException::class)
    fun appendLine(text: String): MessageBuilder
    {
        if (isBuildingMention)
            throw MessageBuilderException("You can't append lines while building mentions.")
        return append("$text \r\n")
    }

    @Synchronized
    @Throws(MessageBuilderException::class)
    fun validate()
    {
        if (isBuildingMention)
            throw MessageBuilderException("Mention is being built.")
        validate("(@[!&]?|#|:(\\w+):)\\d+", false)
        validate("(@[!&]?|#|:(\\w+):)\\d+", true)
    }

    @Synchronized
    fun description(description: String): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.description = description
        return embed(embed)
        //return this
    }

    @Synchronized
    fun color(color: Int): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.color = color
        return embed(embed)
    }

    @Synchronized
    fun title(title: String): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.title = title
        return embed(embed)
    }


    @JvmOverloads
    @Synchronized
    @Throws(MessageBuilderException::class)
    fun field(name: String, value: String, inline: Boolean = false): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        if (embed.fields.size < Settings.FIELD_LIMIT)
            embed.fields.add(Field(name, value, inline))
        else
            throw MessageBuilderException("Too many embed fields!")
        return embed(embed)
    }

    @Synchronized
    fun image(url: String): MessageBuilder
    {
        val image = embed?.image ?: Image()
        image.url = url
        return image(image)
        //return embed(embed)
    }

    @Synchronized
    fun image(image: Image): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.image = image
        return embed(embed)
    }

    @Synchronized
    fun footer(footer: Footer): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.footer = footer
        return embed(embed)
    }

    @Synchronized
    fun footer(text: String): MessageBuilder
    {
        val footer = embed?.footer ?: Footer()
        footer.text = text
        return footer(footer)
    }

    @Synchronized
    fun timestamp(time: Long): MessageBuilder
    {
        return timestamp(Date(time))
    }

    @Synchronized
    fun timestamp(time: Date): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.timestamp = time
        return embed(embed)
    }

    @Synchronized
    fun thumbnail(thumbnail: Thumbnail): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.thumbnail = thumbnail
        return embed(embed)
    }

    @Synchronized
    fun thumbnail(url: String): MessageBuilder
    {
        val thumbnail = embed?.thumbnail ?: Thumbnail()
        thumbnail.url = url
        return thumbnail(thumbnail)
    }

    @Synchronized
    fun video(video: Video): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.video = video
        return embed(embed)
    }

    @Synchronized
    fun video(url: String): MessageBuilder
    {
        val video = embed?.video ?: Video()
        video.url = url
        return video(video)
    }

    @Synchronized
    fun provider(provider: Provider): MessageBuilder
    {
        val embed = getEmbedOrCreate()
        embed.provider = provider
        return embed(embed)
    }

    @Synchronized
    fun provider(name: String, url: String): MessageBuilder
    {
        val provider = Provider()
        provider.name = name
        provider.url = url
        return provider(provider)
    }

    fun attachment(file: File): MessageBuilder
    {
        attachment = file
        return this
    }

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
                throw MessageBuilderException("Incorrectly built mention at index ${index + 1}. Missing required character: $characterToLookFor: \r\n $message \r\n ${"^here".padStart(index)}")
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