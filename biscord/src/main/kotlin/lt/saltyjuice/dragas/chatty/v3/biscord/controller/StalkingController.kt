package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility
import lt.saltyjuice.dragas.chatty.v3.discord.controller.ConnectionController
import lt.saltyjuice.dragas.chatty.v3.discord.controller.DiscordController
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventGuildMemberAdd
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageUpdate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Channel
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.ChannelBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.*


class StalkingController : DiscordController()
{

    @On(EventMessageCreate::class)
    @When("alwaysTrue")
    fun onMessage(request: EventMessageCreate): OPResponse<*>?
    {
        val content = request.data!!
        checkForLinks(content)
        return null
    }

    @On(EventMessageUpdate::class)
    @When("alwaysTrue")
    fun onMessage(request: EventMessageUpdate): OPResponse<*>?
    {
        val content = request.data!!
        checkForLinks(content)
        return null
    }

    @On(EventGuildMemberAdd::class)
    @When("alwaysTrue")
    fun onNewGuildMember(event: EventGuildMemberAdd): OPResponse<*>?
    {
        extractData(event.data!!.user)
        return null
    }

    fun containsID(request: EventMessageCreate): Boolean
    {
        return request.data!!.content.matches(Regex("\\d+"))
    }

    fun checkForLinks(message: Message)
    {
        MessageDeleteCallback(message)
    }

    fun extractData(it: User)
    {
        val age = it.getAge()
        val ageVerbose = getVerboseAge(age)
        val date = Date()
        date.time -= age
        MessageBuilder()
                .appendLine("${it.username}#${it.discriminator}")
                .appendLine("Email: ${it.email}")
                .appendLine("Account age: $age ms (that's $ageVerbose)")
                .appendLine("Account creation date: ${sdf.format(date)}")
                .append("Account is ")
                .apply()
                {
                    if (!it.isVerified)
                        append("not ")
                }
                .append("verified. ")
                .apply()
                {
                    if (!it.isVerified)
                        append("This means that account owner isn't some sort of celebrity or what ever.")
                }
                .appendLine(" ")
                .apply()
                {
                    append("This user does ")
                    if (!it.isTwoFactorAuthentificationEnabled)
                        append("not ")
                    appendLine("have two factor authentification enabled.")
                }
                .send(officeChannel)// 342047989067677699
    }

    fun alwaysTrue(event: Any): Boolean
    {
        return true
    }

    fun getVerboseAge(age: Long): String
    {
        val sb = StringBuilder()
        var age = age
        appendRemainder(sb, "day", age / day)
        age %= day
        appendRemainder(sb, "hour", age / hour)
        age %= hour
        appendRemainder(sb, "minute", age / minute)
        age %= minute
        appendRemainder(sb, "second", age / second, true)

        return sb.toString()
    }

    @JvmOverloads
    fun appendRemainder(sb: StringBuilder, modifier: String, remainder: Long, last: Boolean = false)
    {
        sb.append("$remainder $modifier")
        if (remainder != 1L)
            sb.append("s")
        if (!last)
            sb.append(", ")
    }

    private class MessageDeleteCallback(val message: Message) : Callback<Any>
    {
        private var retryCount = 0

        init
        {
            if (!message.author.isBot)
            {
                val author = ConnectionController.getUser(message.channelId, message.author.id)
                val hasNoRole = author?.roles?.isEmpty() ?: true
                if (message.content.contains(linkRegex) && hasNoRole)
                {
                    Utility.discordAPI.deleteMessage(message.channelId, message.id).enqueue(this)
                }
            }
        }

        override fun onResponse(call: Call<Any>, response: Response<Any>)
        {
            if (response.isSuccessful)
            {
                val dateAsString = sdf.format(message.timestamp)
                Utility.discordAPI.createChannel(ChannelBuilder(message.author.id)).enqueue(object : Callback<Channel>
                {
                    var retryCount = 0
                    override fun onResponse(call: Call<Channel>, response: Response<Channel>)
                    {
                        if (response.isSuccessful)
                        {
                            MessageBuilder()
                                    .appendLine("I'm sorry, but you need to have a region to post links. Don't worry.")
                                    .appendLine("The team were already notified of your presence and message content.")
                                    .send(response.body()!!.id)
                        }
                        else
                        {
                            retry(call)
                        }
                    }

                    override fun onFailure(call: Call<Channel>, t: Throwable)
                    {
                        t.printStackTrace(System.err)
                        retry(call)
                    }

                    fun retry(call: Call<Channel>)
                    {
                        if (retryCount <= 3)
                        {
                            retryCount++
                            call.clone().enqueue(this)
                        }
                    }
                })
                MessageBuilder()
                        .appendLine("@everyone : ATTENTION! Possible spammer detected!")
                        .appendLine("User (id): ${message.author.username}#${message.author.discriminator} (${message.author.id})")
                        .appendLine("MessageID: ${message.id}")
                        .append("Channel: ")
                        .mentionId(message.channelId, MessageBuilder.MentionType.CHANNEL)
                        .appendLine(" ")
                        .appendLine("When: $dateAsString (timezones may apply)")
                        .appendLine("Note: Content may exceed 2k characters, thus it's separated from this warning message.")
                        .send(quarantineChannel)
                MessageBuilder()
                        .append("`")
                        .append(message.content)
                        .append("`")
                        .send(quarantineChannel)
            }
            else
            {
                retry(call)
            }
        }

        override fun onFailure(call: Call<Any>, t: Throwable)
        {
            t.printStackTrace(System.err)
            retry(call)
        }

        fun retry(call: Call<Any>)
        {
            if (retryCount <= 3)
            {
                retryCount++
                call.clone().enqueue(this)
            }
        }
    }

    companion object
    {
        @JvmStatic
        private val second = 1000L

        @JvmStatic
        private val minute = second * 60

        @JvmStatic
        private val hour = minute * 60

        @JvmStatic
        private val day = hour * 24

        @JvmStatic
        private val linkRegex = Regex("((([A-Za-z]{3,9}:(?:\\//)?)(?:[\\-;:&=\\+\\\$,\\w]+@)?[A-Za-z0-9\\.\\-]+|(?:www\\.|[\\-;:&=\\+\\\$,\\w]+@)[A-Za-z0-9\\.\\-]+)((?:\\/[\\+~%\\/\\.\\w\\-_]*)?\\??(?:[\\-\\+=&;%@\\.\\w_]*)#?(?:[\\.\\!\\/\\\\\\w]*))?)")

        @JvmStatic
        private val quarantineChannel = System.getenv("quarantine_id")

        @JvmStatic
        private val officeChannel = System.getenv("office_id")

        @JvmStatic
        private val permittedRoles = System.getenv("roles").split(";")

        @JvmStatic
        private val sdf = SimpleDateFormat("YYYY-MM-dd HH:mm:ss z")
    }

    private fun User.getAge(): Long
    {
        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.timeZone = TimeZone.getTimeZone(ZoneId.ofOffset("GMT", ZoneOffset.ofHours(0)))
        calendar.set(2015, 0, 0)
        calendar.timeInMillis += id.toLong().shr(22)
        return Date().time - calendar.timeInMillis - StalkingController.hour * 24
    }
}

