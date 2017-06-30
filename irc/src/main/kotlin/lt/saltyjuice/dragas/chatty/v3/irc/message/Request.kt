package lt.saltyjuice.dragas.chatty.v3.irc.message

import java.util.concurrent.atomic.AtomicInteger


open class Request(val rawMessage: String)
{
    @Deprecated("Should be handled by implementations")
    val id = atomicID.getAndAdd(1)
    var prefixes: List<String> = ArrayList()
    var arguments: List<String> = ArrayList()

    val user: String
        get()
        {
            return prefixes[1]
        }
    val host: String
        get()
        {
            return prefixes[2]
        }
    val nickname: String
        get()
        {
            return prefixes[0]
        }

    var command = ""

    /*
    request.host = prefixList[2]
        request.user = prefixList[1]
     */

    companion object
    {
        @Deprecated("Should be handled by implementations")
        private val atomicID = AtomicInteger(Int.MIN_VALUE)
    }
}