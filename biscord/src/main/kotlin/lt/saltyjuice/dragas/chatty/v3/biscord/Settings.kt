package lt.saltyjuice.dragas.chatty.v3.biscord

object Settings
{
    @JvmStatic
    val MASHAPE_HEADER = "X-Mashape-Key"

    val MASHAPE_KEY_ENV = "MASHAPE_KEY"

    @JvmStatic
    val MASHAPE_KEY = System.getenv(MASHAPE_KEY_ENV)// ?: throw NullPointerException("$MASHAPE_KEY_ENV environmental variable must be present")
}