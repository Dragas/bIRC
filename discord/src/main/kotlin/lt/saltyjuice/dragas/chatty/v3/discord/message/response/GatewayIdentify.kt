package lt.saltyjuice.dragas.chatty.v3.discord.message.response

import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Identify

class GatewayIdentify(override var data: Identify) : OPResponse<Identify>()
{
    override val op: Int = 2
}