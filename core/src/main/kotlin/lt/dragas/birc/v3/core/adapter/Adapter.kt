package lt.dragas.birc.v3.core.adapter


/**
 * Abstractly implements interfaces for [Serializer] and [Deserializer].
 */
abstract class Adapter<T, R> : Serializer<T>, Deserializer<R>
{
}