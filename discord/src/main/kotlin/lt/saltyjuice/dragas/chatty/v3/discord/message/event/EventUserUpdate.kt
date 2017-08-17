package lt.saltyjuice.dragas.chatty.v3.discord.message.event

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest

open class EventUserUpdate : OPRequest<User>()
{
    companion object
    {
        @JvmStatic
        val EVENT_NAME = "USER_UPDATE"
    }
}