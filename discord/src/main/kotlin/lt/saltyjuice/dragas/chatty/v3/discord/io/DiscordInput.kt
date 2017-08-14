package lt.saltyjuice.dragas.chatty.v3.discord.io

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketInput

interface DiscordInput : WebSocketInput<String, OPRequest<*>>
{
}