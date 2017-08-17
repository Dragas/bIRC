package lt.saltyjuice.dragas.chatty.v3.birc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter

class ShrugController
{
    fun onShrug(request: Request): Response
    {
        return Response(Command.PRIVMSG, request.arguments[0], "¯\\_(ツ)_/¯")
    }

    companion object
    {
        @JvmStatic
        private val instance = ShrugController()

        @JvmStatic
        fun initialize(router: IrcRouter)
        {
            router.add(router.builder().apply {
                type(Command.PRIVMSG)
                testCallback("[shrug]")
                callback(instance::onShrug)
            })
        }
    }
}