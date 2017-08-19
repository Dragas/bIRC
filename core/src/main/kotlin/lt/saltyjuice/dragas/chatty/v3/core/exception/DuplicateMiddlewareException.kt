package lt.saltyjuice.dragas.chatty.v3.core.exception

class DuplicateMiddlewareException(override val message: String = "Particular middleware is already declared for this callback") : Throwable()
{
}