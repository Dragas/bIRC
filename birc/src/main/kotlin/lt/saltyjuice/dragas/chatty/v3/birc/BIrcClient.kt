package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.birc.controller.ModeController
import lt.saltyjuice.dragas.chatty.v3.birc.controller.MovingController
import lt.saltyjuice.dragas.chatty.v3.birc.controller.RandomGeneratorController
import lt.saltyjuice.dragas.chatty.v3.birc.controller.TimedController
import lt.saltyjuice.dragas.chatty.v3.birc.middleware.AddressingMiddleware
import lt.saltyjuice.dragas.chatty.v3.birc.middleware.DiceRollerMiddlerware
import lt.saltyjuice.dragas.chatty.v3.birc.middleware.ModeMiddleware
import lt.saltyjuice.dragas.chatty.v3.birc.middleware.SilentMiddleware
import lt.saltyjuice.dragas.chatty.v3.irc.IrcClient

/**
 * Chatty/IRC implementation for IRC clients.
 *
 * As stated in [IrcClient], this handles concurrency as well as extends some functions
 * for example route generation from json
 */
open class BIrcClient(override val settings: BIrcSettings) : IrcClient(settings)
{
    override val sin: BIrcInput by lazy {
        BIrcInput(adapter, socket.getInputStream())
    }
    override val sout: BIrcOutput by lazy {
        BIrcOutput(adapter, socket.getOutputStream())
    }

    override val router: AsyncRouter  by lazy()
    {
        val router = AsyncRouter()
        router.add(SilentMiddleware(settings))
        router
    }

    override fun initialize()
    {
        super.initialize()
        DiceRollerMiddlerware(settings)
        AddressingMiddleware(settings)
        ModeMiddleware(settings, "TIPS", settings.getMode("tips"))
        ModeController.initialize(router, settings)
        MovingController.initialize(router, settings)
        RandomGeneratorController.initialize(router)
        //ConnectionController.initialize(router, settings)
    }

    override fun onConnect()
    {
        super.onConnect()
        TimedController.initialize(router, settings, sout)
    }

}