package lt.dragas.birc.v3.irc.controller

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.route.IrcRouter
import java.util.concurrent.atomic.AtomicInteger

/**
 * Handles nickname changes when necessary.
 */
class NicknameController(vararg val nicknames: String)
{
    private var lastUsedNickname = AtomicInteger(1)

    fun onNicknameChangeRequest(request: Request): Response
    {
        val nickname = nicknames.getOrNull(lastUsedNickname.getAndAdd(1)) ?: "Botter-${lastUsedNickname.getAndAdd(1)}"
        return Response("nick", nickname)
    }

    companion object
    {
        lateinit var instance: NicknameController
        @JvmStatic
        fun initialize(router: IrcRouter, nicknames: Array<out String>)
        {
            instance = NicknameController(*nicknames)
        }
    }
}