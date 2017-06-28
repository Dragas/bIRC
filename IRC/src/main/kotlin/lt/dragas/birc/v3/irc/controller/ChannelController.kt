package lt.dragas.birc.v3.irc.controller

import lt.dragas.birc.v3.irc.route.IrcRouter

/**
 * Handles channel operations.
 */
class ChannelController private constructor()
{

    companion object
    {
        @JvmStatic
        val instance = ChannelController()

        @JvmStatic
        fun initializeWith(router: IrcRouter)
        {

        }
    }
}