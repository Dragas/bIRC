package lt.saltyjuice.dragas.chatty.v3.irc.controller

import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import lt.saltyjuice.dragas.chatty.v3.irc.model.Channel
import lt.saltyjuice.dragas.chatty.v3.irc.model.User
import lt.saltyjuice.dragas.chatty.v3.irc.route.Command
import lt.saltyjuice.dragas.chatty.v3.irc.route.IrcRouter
import java.util.*

/**
 * Handles channel operations.
 */
class ChannelController private constructor()
{
    val channels = HashMap<String, Channel>()

    fun onChannelJoin(request: Request): Response?
    {

        val channel = getChannel(request, -1) ?: Channel()
        if (request.nickname == ConnectionController.currentNickname)
        {

            channel.name = request.arguments[0]
            channels[channel.name] = channel
        }
        else
        {
            val user = User()
            user.name = request.nickname
            user.host = request.host
            channel.users.add(user)
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

    fun onChannelLeave(request: Request): Response?
    {
        val channel = getChannel(request, -1)
        if (channel != null)
        {
            if (request.nickname == ConnectionController.currentNickname)
            {
                channels.remove(channel.name)
            }
            else
            {
                channel.users.removeIf { it.name == request.nickname }
            }
        }
        return null
    }

    fun onUserNicknameChange(request: Request): Response?
    {
        val original = request.nickname
        val target = request.arguments[0]
        channels.forEach { (key, value) ->
            value.users.forEach {
                if (it.name.endsWith(original))
                    it.name = it.name.replace(original, target)
            }
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
            router.add(router.builder().apply {
                type(Command.PART)
                callback(instance::onChannelLeave)
            })
            router.add(router.builder().apply {
                type(Command.NICK)
                callback(instance::onUserNicknameChange)
            })
        }

        /**
         * A shorthand to return channel in which the bot probably is.
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