package lt.dragas.birc.v3.irc.controller

import lt.dragas.birc.v3.irc.message.Request
import lt.dragas.birc.v3.irc.message.Response
import lt.dragas.birc.v3.irc.model.Channel
import lt.dragas.birc.v3.irc.model.User
import lt.dragas.birc.v3.irc.route.Command
import lt.dragas.birc.v3.irc.route.IrcRouter
import java.util.*

/**
 * Handles channel operations.
 */
class ChannelController private constructor()
{
    val channels = HashMap<String, Channel>()

    fun onChannelJoin(request: Request): Response?
    {
        if (request.nickname == NicknameController.currentNickname)
        {
            val channel = Channel()
            channel.name = request.arguments[0]
            channels[channel.name] = channel
        }
        return null
    }

    fun onChannelTopic(request: Request): Response?
    {
        val channel = getChannel(request)
        if (channel != null)
            channel.topic.content = request.arguments[2]
        return null
    }

    fun onChannelUsers(request: Request): Response?
    {
        val channel = getChannel(request, 1)
        if (channel != null)
        {
            val users = request.arguments.last().split(" ")
            val toAdd = ArrayList<User>()
            users.forEach { it ->
                val user = channel.users.firstOrNull { user -> user.name == it }
                if (user == null)
                {
                    val userToAdd = User()
                    userToAdd.name = it
                    toAdd.add(userToAdd)
                }
            }
            channel.users.addAll(toAdd)
        }
        return null
    }

    fun onChannelTopicDate(request: Request): Response?
    {
        val channel = getChannel(request)
        if (channel != null)
        {
            channel.topic.setBy = request.arguments[2]
            channel.topic.setOn = request.arguments[3]
        }
        return null
    }

    private fun getChannel(request: Request, offset: Int = 0): Channel?
    {
        return channels[request.arguments[1 + offset]]
    }

    companion object
    {
        @JvmStatic
        val instance = ChannelController()

        @JvmStatic
        fun initialize(router: IrcRouter)
        {
            router.`when`(Command.RPL_TOPIC, instance::onChannelTopic)
            router.`when`(Command.RPL_TOPICBY, instance::onChannelTopicDate)
            router.`when`(Command.JOIN, instance::onChannelJoin)
            router.`when`(Command.RPL_NAMEREPLY, instance::onChannelUsers)
        }
    }
}