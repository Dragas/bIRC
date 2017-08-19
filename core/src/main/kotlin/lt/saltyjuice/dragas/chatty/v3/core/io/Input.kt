package lt.saltyjuice.dragas.chatty.v3.core.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import java.io.InputStream

/**
 * Contains default methods needed for [InputStream] to function.
 *
 * This particular class only contains [adapter] field and a method [getRequest]. For proper behavior
 * they should be implemented accordingly depending on protocol.
 */
interface Input<InputBlock, Request>
{
    /**
     * Used to deserialize [InputBlock] type requests from server to something more usable by implementations
     */
    val adapter: Deserializer<InputBlock, Request>
    /**
     * Returns a request from provided adapter. Do note that the implementing class should
     * also implement a method of getting actual data for this.
     */
    fun getRequest(): Request
}