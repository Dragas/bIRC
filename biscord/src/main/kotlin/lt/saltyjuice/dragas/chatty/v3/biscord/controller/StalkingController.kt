package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import lt.saltyjuice.dragas.chatty.v3.biscord.middleware.MentionsMe
import lt.saltyjuice.dragas.chatty.v3.core.route.Before
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import java.util.*


class StalkingController : CommandController()
{
    override val command: String = "dox"

    @On(EventMessageCreate::class)
    @When("shouldRespond")
    @Before(MentionsMe::class)
    override fun onCommand(requst: EventMessageCreate): OPResponse<*>?
    {
        requst.data!!
                .mentionedUsers
                .forEach()
                {
                    val age = it.getAge()
                    val ageVerbose = getVerboseAge(age)

                    MessageBuilder()
                            .appendLine("${it.username}#${it.discriminator}")
                            .appendLine("Email: ${it.email}")
                            .appendLine("Account age: $age ms (that's $ageVerbose)")
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
                            .send("344789216045170690")// 342047989067677699
                }
        return null
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

    override fun shouldRespond(request: EventMessageCreate): Boolean
    {
        return super.shouldRespond(request) && request.data!!.mentionedUsers.isNotEmpty()
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
    }
}

private fun User.getAge(): Long
{
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(2015, 0, 0)
    calendar.timeInMillis += id.toLong().shr(22)
    return Date().time - calendar.timeInMillis
}