package lt.saltyjuice.dragas.chatty.v3.biscord

object Settings
{
    @JvmStatic
    val MASHAPE_KEY = System.getenv("MASHAPE_KEY") ?: throw NullPointerException("MASHAPE_KEY environmental variable must be present")

    @JvmStatic
    val MASHAPE_HEADER = "X-Mashape-Key"
}