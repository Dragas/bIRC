package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.discord.main.DiscordClient
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit

class BiscordClient(gatewayInit: GatewayInit) : DiscordClient(gatewayInit)
{
    override fun initialize()
    {
        super.initialize()
        ResponseController.initialize(router)
    }
}