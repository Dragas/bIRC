package lt.saltyjuice.dragas.chatty.v3.irc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.IrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter
import java.util.concurrent.atomic.AtomicInteger

/**
 * Handles nickname changes when necessary.
 */
@Deprecated("should be handled by implementations")
class NicknameController()
{
    private var lastUsedNickname = AtomicInteger(0)
    private var currentNickname = ""

    fun onNicknameChangeRequest(request: Request): Response
    {
        val nickname = settings.nicknames.getOrElse(lastUsedNickname.getAndAdd(1), { it -> "${settings.nicknames[0]}-$it" })
        return Response(Command.NICK, nickname)
    }

    fun onNicknameInitialize(request: Request): Response
    {
        val response = onNicknameChangeRequest(request)
        currentNickname = response.arguments[0]
        val userResponse = Response(Command.USER, settings.user, settings.mode, "*", ":${settings.realname}")
        response.otherResponses.add(userResponse)
        return response
    }

    /**
     * Assumes self nickname change.
     */
    fun onNicknameChange(request: Request): Response?
    {
        if (request.nickname == currentNickname)
            currentNickname = request.arguments[0]
        return null
    }

    companion object
    {

        @JvmStatic
        val instance: NicknameController by lazy()
        {
            val controller = NicknameController()
            controller
        }

        @JvmStatic
        private var settings: IrcSettings = IrcSettings()

        @JvmStatic
        fun initialize(router: IrcRouter, settings: IrcSettings)
        {
            Companion.settings = settings
            router.add(router.builder().let {
                it.type(Command.ERR_NICKCOLLISION)
                it.callback(instance::onNicknameChangeRequest)
                it.build()
            })
            router.add(router.builder().let {
                it.callback(instance::onNicknameChangeRequest)
                it.type(Command.ERR_NICKNAMEINUSE)
                it.build()
            })
            router.add(router.builder().let {
                it.callback(instance::onNicknameInitialize)
                it.type(Command.ERR_NOTREGISTERED)
                it.build()
            })
            router.add(router.builder().let {
                it.callback(instance::onNicknameInitialize)
                it.type(Command.NOTICE)
                it.middleware("AUTH")
                it.build()
            })
            router.add(router.builder().let {
                it.callback(instance::onNicknameChange)
                it.type(Command.NICK)
                it.build()
            })


        }

        @JvmStatic
        val currentNickname: String
            get()
            {
                return instance.currentNickname
            }
    }
}