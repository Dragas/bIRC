package lt.saltyjuice.dragas.chatty.v3.birc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter
import java.util.*
import java.util.regex.Pattern


class RandomGeneratorController
{
    private val generator = Random()

    fun onRandomRequest(request: Request): Response
    {
        val args = request.arguments[1].split("d", limit = 2)
        val count = Integer.parseInt(args[0])
        val limit = Integer.parseInt(obtainArguments(args[1], "\\d+")[0])
        var sum = 0
        val sb = StringBuilder()
        sb.append(request.nickname)
        sb.append(": ")
        val mod = obtainArguments(args[1], "(\\+|-)\\d+").sumBy { Integer.parseInt(it) }
        for (i in 0..count - 1)
        {
            if (i >= 1)
            {
                sb.append(", ")
            }
            val generated = generator.nextInt(limit) + 1 + mod
            sum += generated
            sb.append(generated)
        }

        if (count > 1)
        {
            sb.append(". Sum: $sum")
        }
        if (mod != 0)
        {
            sb.append(". Mod: $mod")
        }
        val response = Response("")

        splitBy(sb.toString(), 512).forEachIndexed { index, text ->
            response.otherResponses.add(Response(Command.PRIVMSG, request.arguments[0], text))
        }

        return response
    }

    private fun obtainArguments(source: String, pattern: String): Array<String>
    {
        val cPattern = Pattern.compile(pattern)
        val matcher = cPattern.matcher(source)
        val arrayList = ArrayList<String>()
        while (matcher.find())
        {
            arrayList.add(matcher.group())
        }
        return arrayList.toTypedArray()
    }

    private fun splitBy(source: String, length: Int): Array<String>
    {
        val mod = if (source.length % length > 0) 1 else 0
        val array = Array(source.length / length + mod, { "" })
        var iterator = 0
        while (iterator < array.size)
        {
            val realend = (iterator + 1) * length
            val end = if (realend > source.length) source.length else realend
            array[iterator] = source.substring(iterator * length, end)
            iterator++
        }
        return array
    }

    companion object
    {
        @JvmStatic
        val instance = RandomGeneratorController()

        @JvmStatic
        fun initialize(router: IrcRouter)
        {
            router.add(router.builder().let {
                it.testCallback("\\d+d\\d+((\\+|-)\\d+)*")
                it.callback(instance::onRandomRequest)
                it.type(Command.PRIVMSG)
                it.build()
            })
        }
    }
}
