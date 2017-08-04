package lt.saltyjuice.dragas.chatty.v3.birc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import java.util.*
import java.util.regex.Pattern


open class RandomGeneratorController
{
    fun onRandomRequest(request: Request): Response
    {
        val generator = Random()
        val matcher = Pattern.compile(pattern).matcher(request.arguments[1])
        val response = Response("")
        while (matcher.find())
        {
            val rollArguments = matcher.group()
            val count = getCount(rollArguments)
            val limit = getLimit(rollArguments)
            if(Math.max(count, limit) <= 0)
                return response
            val modifier = getModifier(rollArguments)
            var sum: Int = modifier
            repeat(count) {
                sum += generator.nextInt(limit) + 1
            }
            val stringbuilder = StringBuilder()
            stringbuilder.append(request.arguments[1])
            response.otherResponses.add(generateResponse(request, "$sum"))
        }
        return response
    }

    protected fun getArgument(arguments: String, pattern: String): String
    {
        val matcher = Pattern.compile(pattern).matcher(arguments)
        if (matcher.find())
        {
            return matcher.group()
        }
        return ""
    }

    protected fun getCount(arguments: String): Int
    {
        var returnable = -1
        val argument = getArgument(arguments, "${countPattern}d").replace("d", "")
        if (argument.isNotBlank())
        {
            try
            {
                returnable = argument.toInt()
            }
            catch (err: Exception)
            {
                print(err)
                returnable = -2
            }
        }
        return returnable
    }

    protected fun getLimit(arguments: String): Int
    {
        var returnable = -1
        val argument = getArgument(arguments, "d$limitPattern").replace("d", "")
        if(argument.isNotBlank())
        {
            try
            {
                returnable = argument.toInt()
            }
            catch (err: Exception)
            {
                print(err)
                returnable = -2
            }
        }
        return returnable
    }

    protected fun getModifier(arguments: String): Int
    {
        var returnable = 0
        val argument = getArgument(arguments, "($modifierPattern)+")
        val matcher = Pattern.compile(modifierPattern).matcher(argument)
        while(matcher.find())
        {
            try
            {
                returnable += matcher.group().toInt()
            }
            catch (err : Exception)
            {
                print(err)
                break
            }
        }
        return returnable
    }

    protected fun generateResponse(request: Request, sum: String): Response
    {
        val response = Response(Command.PRIVMSG, request.arguments[0], "${request.nickname}: $sum.")
        return response
    }

    companion object
    {
        @JvmStatic
        val instance = RandomGeneratorController()

        @JvmStatic
        fun initialize(router: IrcRouter)
        {
            router.add(router.builder().let {
                it.testCallback(pattern)
                it.middleware("DICEROLLAN")
                it.callback(instance::onRandomRequest)
                it.type(Command.PRIVMSG)
                it.build()
            })
        }

        @JvmStatic
        protected val countPattern = "\\d+"

        @JvmStatic
        protected val limitPattern = "\\d+"

        @JvmStatic
        protected val modifierPattern = "([+-])\\d+"

        @JvmStatic
        protected val modifiersPattern = "($modifierPattern)*"

        @JvmStatic
        protected val pattern = "(?<!\\S)${countPattern}d${limitPattern}${modifiersPattern}(?!\\S)"
    }
}
