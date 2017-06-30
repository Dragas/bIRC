package lt.saltyjuice.dragas.chatty.v3.birc

import lt.saltyjuice.dragas.chatty.v3.birc.controller.RandomGeneratorController
import lt.saltyjuice.dragas.chatty.v3.irc.IrcClient
import lt.saltyjuice.dragas.chatty.v3.irc.controller.ConnectionController

/**
 * Chatty/IRC implementation for IRC clients.
 *
 * As stated in [IrcClient], this handles concurrency as well as extends some functions
 * for example route generation from json
 */
class BIrcClient(override val settings: BIrcSettings) : IrcClient(settings)
{
    override val sin: BIrcInput by lazy {
        BIrcInput(adapter, socket.getInputStream())
    }
    override val sout: BIrcOutput by lazy {
        BIrcOutput(adapter, socket.getOutputStream())
    }

    override fun initialize()
    {
        super.initialize()
        RandomGeneratorController.initialize(router)
        ConnectionController.initialize(router, settings)
    }
}