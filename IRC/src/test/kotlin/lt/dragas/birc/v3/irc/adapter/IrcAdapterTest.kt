package lt.dragas.birc.v3.irc.adapter


import lt.dragas.birc.v3.irc.message.Response
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class IrcAdapterTest
{
    var adapter = IrcAdapter()

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
        val response = Response("privmsg #purple hey") // feels pointless
        val serialized = adapter.serialize(response)
        Assert.assertEquals("privmsg #purple hey", serialized)
    }

    @Test
    fun deserializesPingRequest()
    {
        val message = "PING :1745AB"
        val deserialized = adapter.deserialize(message)
        Assert.assertEquals("PING", deserialized.command)
        Assert.assertEquals("1745AB", deserialized.arguments[0])
    }

    @Test
    fun deserializesRequestWithCodeAndMysteriousAddon()
    {
        //                                                     v that is the mysterious addon
        val message = ":hollywood.purplesurge.com 252 niceman 11 :operator(s) online"
        val requestType = "252"
        val requestMessage = "operator(s) online"
        val target = "niceman"
        val origin = "hollywood.purplesurge.com"
        val request = adapter.deserialize(message)
        Assert.assertEquals(requestType, request.command)
        //Assert.assertEquals(target, request.target) // one of arguments
        //Assert.assertEquals(requestMessage, request.message)
        //Assert.assertEquals(origin, request.origin)
        TODO("There's still no internal support for this")
    }

    @Test
    fun deserializesRequestWithColor()
    {
        val message = ":hollywood.purplesurge.com 372 niceman :- 12As our motto suggests, PurpleSurge rives to be"
        val requestType = 372
        val requestMessage = "As our motto suggests, PurpleSurge rives to be"
        val target = "niceman"
        val origin = "hollywood.purplesurge.com"
        val request = adapter.deserialize(message)/*
        Assert.assertEquals(requestType, request.type)
        Assert.assertEquals(target, request.target)
        Assert.assertEquals(requestMessage, request.message)
        Assert.assertEquals(origin, request.origin)*/
        TODO("There's still no internal support for this")
    }

    @Test
    fun deserializesRequestWithCode()
    {
        val message = ":services.purplesurge.com 372 niceman :- without prior written approval."
        val requestType = 372
        val requestMessage = "- without prior written approval."
        val target = "niceman"
        val origin = "services.purplesurge.com"
        val request = adapter.deserialize(message)/*
        Assert.assertEquals(requestType, request.type)
        Assert.assertEquals(target, request.target)
        Assert.assertEquals(requestMessage, request.message)
        Assert.assertEquals(origin, request.origin)*/
        TODO("There's still no internal support for this")
    }

    @Test
    fun deserializesRequestToSelf()
    {
        val message = ":niceman!man@AA3DA92D.A1380E30.9FA3D578.IP PRIVMSG niceman :asdf"
        val requestType = "PRIVMSG"
        val origin = "man@AA3DA92D.A1380E30.9FA3D578.IP"
        val username = "niceman"
        val target = "niceman"
        val requestMessage = "asdf"
        val request = adapter.deserialize(message)/*
        Assert.assertEquals(requestType, request.type)
        Assert.assertEquals(target, request.target)
        Assert.assertEquals(requestMessage, request.message)
        Assert.assertEquals(origin, request.origin)
        Assert.assertEquals(username, request.username)*/
        TODO("There's still no internal support for this")
    }

    @Test
    fun deserializesRequestToOther()
    {
        val message = ":niceman!man@AA3DA92D.A1380E30.9FA3D578.IP PRIVMSG notniceman :asdf"
        val requestType = "PRIVMSG"
        val origin = "man@AA3DA92D.A1380E30.9FA3D578.IP"
        val username = "niceman"
        val target = "notniceman"
        val requestMessage = "asdf"
        val request = adapter.deserialize(message)/*
        Assert.assertEquals(requestType, request.type)
        Assert.assertEquals(target, request.target)
        Assert.assertEquals(requestMessage, request.message)
        Assert.assertEquals(origin, request.origin)
        Assert.assertEquals(username, request.username)*/
        TODO("There's still no internal support for this")
    }

    @Test
    fun deserializesRequestToChannel()
    {
        val message = ":niceman!man@AA3DA92D.A1380E30.9FA3D578.IP PRIVMSG #notnicemanchannel :asdf"
        val requestType = "PRIVMSG"
        val origin = "man@AA3DA92D.A1380E30.9FA3D578.IP"
        val username = "niceman"
        val target = "#notnicemanchannel"
        val requestMessage = "asdf"
        val request = adapter.deserialize(message)/*
        Assert.assertEquals(requestType, request.type)
        Assert.assertEquals(target, request.target)
        Assert.assertEquals(requestMessage, request.message)
        Assert.assertEquals(origin, request.origin)
        Assert.assertEquals(username, request.username)*/
        TODO("There's still no internal support for this")
    }

}