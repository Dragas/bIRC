package lt.dragas.birc.v3.core.adapter


/**
 * An interface noting that implementing class can serialize responses to server.
 *
 * Since there are many formats in how you can transfer data, the implementation is left for
 * particular application.
 */
interface Serializer<in T>
{
    /**
     * Serializes response to server from something that was used in application.
     */
    fun serialize(any: T): String //FIXME: Is response always string?
}