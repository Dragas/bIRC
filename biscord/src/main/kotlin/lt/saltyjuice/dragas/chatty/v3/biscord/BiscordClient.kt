package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.core.route.UsesControllers
import lt.saltyjuice.dragas.chatty.v3.discord.main.DiscordClient
import lt.saltyjuice.dragas.chatty.v3.discord.message.request.GatewayInit


@UsesControllers(ResponseController::class)
class BiscordClient(gatewayInit: GatewayInit) : DiscordClient(gatewayInit)
{

}