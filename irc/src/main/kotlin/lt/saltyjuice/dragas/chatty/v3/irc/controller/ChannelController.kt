package lt.saltyjuice.dragas.chatty.v3.irc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.model.Channel
import lt.saltyjuice.dragas.chatty.v3.irc.model.User
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.routing.IrcRouter
import java.util.*

/**
 * Handles channel operations.
 */
class ChannelController private constructor()
{
    val channels = HashMap<String, Channel>()

    fun onChannelJoin(request: Request): Response?
    {
        if (request.nickname == ConnectionController.currentNickname)
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
            router.add(router.builder().let {
                it.type(Command.RPL_TOPIC)
                it.callback(instance::onChannelTopic)
                it.build()
            })
            router.add(router.builder().let {
                it.type(Command.RPL_TOPICBY)
                it.callback(instance::onChannelTopicDate)
                it.build()
            })
            router.add(router.builder().let {
                it.type(Command.JOIN)
                it.callback(instance::onChannelJoin)
                it.build()
            })
            router.add(router.builder().let {
                it.type(Command.RPL_NAMEREPLY)
                it.callback(instance::onChannelUsers)
                it.build()
            })
        }

        /**
         * A shorthand to return channel in which implementation probably is.
         * @param channelName string based channel name, usually starting with #.
         * @return Channel model if implementation is in that channel, otherwise null.
         */
        @JvmStatic
        fun getChannel(channelName: String): Channel?
        {
            return instance.channels[channelName]
        }
    }
}