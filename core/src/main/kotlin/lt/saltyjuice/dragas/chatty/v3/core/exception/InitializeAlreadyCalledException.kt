package lt.saltyjuice.dragas.chatty.v3.core.exception

class InitializeAlreadyCalledException(message: String = "super.initialize() was already called") : Exception(message)
{
}