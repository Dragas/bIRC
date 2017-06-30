package lt.saltyjuice.dragas.chatty.v3.core.adapter


/**
 * Abstractly implements interfaces for [Serializer] and [Deserializer].
 */
abstract class Adapter<Request, Response> : Deserializer<Request>, Serializer<Response>
{

}