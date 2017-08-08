package lt.saltyjuice.dragas.chatty.v3.birc.controller

import lt.saltyjuice.dragas.chatty.v3.birc.BIrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter

class MovingController
{

    fun onJoinRequest(request: Request): Response
    {
        val channelToJoin = request.arguments.last().substringAfter("join ")
        return Response(Command.JOIN, channelToJoin)
    }

    fun onJoin(request: Request): Response?
    {
        val channel = request.arguments[0]
        /*if (ConnectionController.currentNickname == request.nickname)
            return Response(Command.PRIVMSG, channel, "HELLO WORLD!")*/
        return /*Response()*/ null
    }

    fun onLeaveThisChannel(request: Request): Response
    {
        val channel = request.arguments[0]
        return Response(Command.PART, channel)
    }

    companion object
    {
        @JvmStatic
        val instance: MovingController = MovingController()

        @JvmStatic
        private lateinit var settings: BIrcSettings

        @JvmStatic
        fun initialize(router: IrcRouter, settings: BIrcSettings)
        {
            this.settings = settings
            router.add(router.builder().apply {
                type(Command.PRIVMSG)
                testCallback("join [#\\+!&]\\w{1,50}+$")
                callback(instance::onJoinRequest)
                middleware("ADDRESS")
            })
            router.add(router.builder().apply {
                type(Command.JOIN)
                testCallback("")
                callback(instance::onJoin)
            })
            router.add(router.builder().apply {
                type(Command.PRIVMSG)
                testCallback("leave$")
                middleware("ADDRESS")
                callback(instance::onLeaveThisChannel)
            })
        }
    }
}