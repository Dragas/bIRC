package lt.saltyjuice.dragas.chatty.v3.websocket.exception

import java.util.concurrent.CancellationException

class ServerDestroyedException(message: String) : CancellationException(message)
{
}