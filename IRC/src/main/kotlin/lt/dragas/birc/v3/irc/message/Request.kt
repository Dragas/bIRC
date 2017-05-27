package lt.dragas.birc.v3.irc.message


class Request() : Message()
{
    var origin: String = ""
    var type: Int = NONE
    /**
     * Default mode. Means that route does not work solely with private messages or channel messages.
     */
    companion object
    {
        @JvmField
        val NONE: Int = 0
        /**
         * Marks route as ping route. Shouldn't be used outside [Pong]
         */
        @JvmField
        val PING: Int = 4
        /**
         * Marks route as private message route.
         */
        @JvmField
        val PRIVATE: Int = 1
        /**
         * Marks route as channel route.
         */
        @JvmField
        val CHANNEL: Int = 2

    }
}