package lt.dragas.birc.v3.core.adapter


/**
 * Interface noting that particular class can deserialize responses from server.
 *
 * Since there are many formats on how responses are generated, be it IRC, twitch, discord,
 * twitter, reddit, etc., this interface is left for implementation for particular case.
 *
 */
interface Deserializer<out T>
{
    /**
     * Deserializes response from server to something more usable in application.
     */
    fun deserialize(block: String): T // FIXME: Is block always a string?
}