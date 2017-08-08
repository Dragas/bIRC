package lt.saltyjuice.dragas.chatty.v3.birc.controller

import lt.saltyjuice.dragas.chatty.v3.birc.AsyncRouter
import lt.saltyjuice.dragas.chatty.v3.birc.BIrcOutput
import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import java.util.*

class TimedController
{
    //private val blockingQueue = ArrayBlockingQueue<Job>()
    private val generator = Random()

    fun onTip(request: Request): Response
    {
        val size = settings.tips.size
        val tip = settings.tips[generator.nextInt(size)]
        val channel = request.arguments[0]
        return Response(Command.PRIVMSG, channel, tip)
    }

    fun onAddTip(request: Request): Response
    {
        val tip = parseTip(request.arguments.last())
        settings.tips.add(tip)
        return Response(Command.PRIVMSG, request.arguments[0], "Added a tip: $tip")
    }

    private fun parseTip(message: String): String
    {
        return message.substringAfter("tip ")
    }

    companion object
    {
        @JvmStatic
        val instance: TimedController by lazy()
        {
            TimedController()
        }

        @JvmStatic
        private lateinit var settings: BIrcSettings

        @JvmStatic
        fun initialize(router: AsyncRouter, settings: BIrcSettings, output: BIrcOutput)
        {
            this.settings = settings
            router.add(router.builder().apply {
                type(Command.PRIVMSG)
                testCallback("tip me$")
                middleware("TIPS")
                middleware("ADDRESS")
                callback(instance::onTip)
            })
            router.add(router.builder().apply {
                type(Command.PRIVMSG)
                testCallback("add tip (?!\\s*$).+")
                middleware("TIPS")
                middleware("ADDRESS")
                callback(instance::onAddTip)
            })
        }
    }
}