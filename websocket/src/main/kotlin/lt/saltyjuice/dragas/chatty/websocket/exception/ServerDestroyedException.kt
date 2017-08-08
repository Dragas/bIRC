package lt.saltyjuice.dragas.chatty.websocket.exception

import java.util.concurrent.CancellationException

class ServerDestroyedException(message: String) : CancellationException(message)
{
}