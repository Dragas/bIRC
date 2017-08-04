package lt.saltyjuice.dragas.chatty.v3.birc.controller

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command

class ModeController
{
    fun onEnableMode(request: Request): Response?
    {
        val mode = parseMode(request.arguments.last())
        val channel = request.arguments[0]
        if (mode == "silent")
        {
            settings.currentMode = 0
            return null
        }
        val modeAsInt = settings.getMode(mode)
        var message = "I am the $mode"
        if (modeAsInt == -1)
            message = "Unknown mode: $mode"
        else if (settings.currentMode.and(modeAsInt) == modeAsInt)
            message = "I already am the $mode"
        else
            settings.currentMode = settings.currentMode.or(modeAsInt)
        return Response(Command.PRIVMSG, channel, message)
    }

    fun onDisableMode(request: Request): Response?
    {
        val mode = parseMode(request.arguments.last())
        val channel = request.arguments[0]
        if (mode == "silent")
            return null
        val modeAsInt = settings.getMode(mode)
        var message = "I was never $mode to begin with."
        if (settings.currentMode.and(modeAsInt) == modeAsInt)
        {
            settings.currentMode = settings.currentMode.xor(modeAsInt)
            message = "I am no longer $mode"
        }
        return Response(Command.PRIVMSG, channel, message)
    }

    private fun parseMode(argument: String): String
    {
        return argument.split(Regex(" [\\+-]"), limit = 2).getOrElse(1, { it -> "" }).toLowerCase()
    }

    companion object
    {
        @JvmStatic
        val instance = ModeController()

        @JvmStatic
        private var settings = BIrcSettings()

        @JvmStatic
        fun initialize(router: IrcRouter, ircSettings: BIrcSettings)
        {
            this.settings = ircSettings
            router.add(router.builder().let {
                it.type(Command.PRIVMSG)
                it.testCallback("mode \\+")
                it.callback(instance::onEnableMode)
            })
            router.add(router.builder().let {
                it.type(Command.PRIVMSG)
                it.testCallback("mode -")
                it.callback(instance::onDisableMode)
            })
        }
    }
}