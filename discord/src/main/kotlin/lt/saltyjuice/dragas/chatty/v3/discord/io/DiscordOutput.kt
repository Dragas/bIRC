package lt.saltyjuice.dragas.chatty.v3.discord.io

import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import lt.saltyjuice.dragas.chatty.v3.websocket.io.WebSocketOutput

interface DiscordOutput : WebSocketOutput<OPResponse<*>, String>
{

}