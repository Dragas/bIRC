package lt.dragas.birc.v3.irc.adapter


import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class IrcAdapterTest
{
    val adapter = IrcAdapter()
    var request: Request = Request("")
    @Test
    fun serializeSimpleResponse()
    {
        val response = Response("#purple", "Hey")
        val serialized = adapter.serialize(response)
        Assert.assertEquals("privmsg #purple Hey", serialized)
    }

    @Test
    fun serializesRawResponse()
    {
        val response = Response("kek #purple hey") // feels pointless
        val serialized = adapter.serialize(response)
        Assert.assertEquals("kek #purple hey", serialized)
    }

    @Test
    fun deserializesPingRequest()
    {
        val message = "PING :1745AB"
        request = adapter.deserialize(message)
        val type = "PING"
        val longArgument = "1745AB"
        Assert.assertEquals(type, request.command)
        Assert.assertEquals(longArgument, request.arguments[0])
    }

    @Test
    fun deserializesRequestFromServer()
    {
        //                                                     v that is the mysterious addon
        val message = ":hollywood.purplesurge.com 252 niceman 11 :operator(s) online"// ^ is a retard. That's an argument
        val requestType = "252"
        val requestMessage = "operator(s) online"
        val target = "niceman"
        val origin = "hollywood.purplesurge.com"
        val request = adapter.deserialize(message)
        Assert.assertEquals(requestType, request.command)
        Assert.assertEquals(target, request.arguments[0])
        Assert.assertEquals("11", request.arguments[1])
        Assert.assertEquals(requestMessage, request.arguments[2])
        Assert.assertEquals(origin, request.nickname)
    }

    @Test
    fun deserializesRequestFromSomeone()
    {
        val message = ":niceman1!man@AA3DA92D.A1380E30.9FA3D578.IP PRIVMSG niceman :asdf"
        val requestType = "PRIVMSG"
        val user = "man"
        val host = "AA3DA92D.A1380E30.9FA3D578.IP"
        val nickname = "niceman1"
        val target = "niceman"
        val requestMessage = "asdf"
        request = adapter.deserialize(message)

        Assert.assertEquals(requestType, request.command)
        Assert.assertEquals(user, request.user)
        Assert.assertEquals(host, request.host)
        Assert.assertEquals(nickname, request.nickname)
        Assert.assertEquals(target, request.arguments[0])
        Assert.assertEquals(requestMessage, request.arguments[1])
    }
}