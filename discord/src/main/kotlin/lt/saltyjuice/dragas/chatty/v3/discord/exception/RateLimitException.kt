package lt.saltyjuice.dragas.chatty.v3.discord.exception

import java.lang.Exception


class RateLimitException @JvmOverloads constructor(message: String = "Rate limit exceeded.") : Exception(message)
{
}