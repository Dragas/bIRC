package lt.saltyjuice.dragas.chatty.v3.core.adapter


/**
 * Abstractly implements interfaces for [Serializer] and [Deserializer].
 *
 * Usually, [InputBlock] and [OutputBlock] will match in types, though it's better safe than sorry
 * to separate them.
 */
abstract class Adapter<InputBlock, Request, Response, OutputBlock> : Deserializer<InputBlock, Request>, Serializer<Response, OutputBlock>
{

}