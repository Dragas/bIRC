package lt.saltyjuice.dragas.chatty.v3.irc.message


open class Request(val rawMessage: String)
{
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
}