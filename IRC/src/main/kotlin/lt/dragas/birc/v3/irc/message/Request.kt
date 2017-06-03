package lt.dragas.birc.v3.irc.message


class Request(var rawMessage: String)
{
    var user = ""
    var host = ""
    var nickname = ""
    var command = ""
    var arguments: List<String> = ArrayList()
}