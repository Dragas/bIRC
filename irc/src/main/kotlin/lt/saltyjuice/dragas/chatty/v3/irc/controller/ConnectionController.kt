package lt.saltyjuice.dragas.chatty.v3.irc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.IrcSettings
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter
import java.util.concurrent.atomic.AtomicInteger

/**
 * Handles connecting to channels once RPL welcome message is hit.
 */
open class ConnectionController
{
    private var lastUsedNickname = AtomicInteger(0)
    private var currentNickname = ""


    open fun onNicknameChangeRequest(request: Request): Response
    {
        val nickname = settings.nicknames.getOrElse(lastUsedNickname.getAndAdd(1), { it -> "${settings.nicknames[0]}-$it" })
        return Response(Command.NICK, nickname)
    }

    open fun onNicknameInitialize(request: Request): Response
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
    open fun onNicknameChange(request: Request): Response?
    {
        if (request.nickname == currentNickname)
            currentNickname = request.arguments[0]
        return null
    }

    open fun onAuthenticate(request: Request): Response // authentication implies that bot has successfully gained permissions to send commands to target server
    {
        val response = Response("")
        settings.channels.forEach {
            response.otherResponses.add(Response(Command.JOIN, it))
        }
        return response
    }

    open fun onPing(request: Request): Response
    {
        val destination = request.arguments.last()
        /*if (request.arguments.size > 1)
            destination = request.arguments[1]*/
        return Response(Command.PONG, destination)
    }

    companion object
    {
        @JvmStatic
        val instance = ConnectionController()

        @JvmStatic
        private var settings: IrcSettings = IrcSettings()

        @JvmStatic
        fun initialize(router: IrcRouter, settings: IrcSettings)
        {
            this.settings = settings
            router.add(router.builder().let {
                it.callback(instance::onAuthenticate)
                it.type(Command.RPL_WELCOME)
                it.build()
            })
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
            router.add(router.builder().let {
                it.type(Command.PING)
                it.callback(instance::onPing)
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