package lt.dragas.birc.v3.irc.adapter

import lt.dragas.birc.v3.core.adapter.Adapter
import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response

class IrcAdapter : Adapter<Request, Response>()
{
    override fun serialize(any: Response): String
    {
        val response = if (any.rawMessage == "") "privmsg ${any.target} ${any.message}" else any.rawMessage
        return response
    }

    override fun deserialize(block: String): Request
    {
        val request = Request()
        request.apply {
            message = rawMessage.substring(rawMessage.indexOf(" :") + 2)
            if (rawMessage.startsWith("ping", true))
            {
                type = Request.PING
            }
            else
            {
                try
                {
                    val array = rawMessage.take(rawMessage.indexOf(" :")).split(" ")
                    username = array[0].substring(1, array[0].indexOf("!"))
                    target = array[2]
                    if (target.startsWith("#"))
                        type = Request.CHANNEL//channel
                    else
                        type = Request.PRIVATE
                    target = username

                }
                catch(err: Exception)
                {
                    err.printStackTrace()
                }
            }
        }
        return request
    }
}