package lt.saltyjuice.dragas.chatty.v3.core.adapter


/**
 * Interface noting that particular class can deserialize responses from server.
 *
 * Since there are many formats on how responses are generated, be it IRC, twitch, discord,
 * twitter, reddit, etc., this interface is left for implementation for particular case.
 *
 */
interface Deserializer<Block, out Request>
{
    /**
     * Deserializes response from server to something more usable in application.
     * @param block a string block which is received from server
     * @return a deserialized [Request] object
     */
    fun deserialize(block: Block): Request
}