package lt.saltyjuice.dragas.chatty.v3.core.io

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Deserializer
import java.io.InputStream

/**
 * Contains default methods needed for [InputStream] to function.
 *
 * This particular class only contains two fields: [adapter] and [getRequest]. For proper behavior
 * they should be implemented accordingly.
 *
 * @param adapter used to deserialize [InputBlock] type requests from server to something
 * more usable by implementations
 */
abstract class Input<InputBlock, Request>(protected open val adapter: Deserializer<InputBlock, Request>)
{
    //protected val sin = Scanner(inputStream)

    /**
     * Returns a request from provided adapter. Do note that the implementing class should
     * also implement a method of getting actual data for this.
     */
    abstract fun getRequest(): Request
    /*{
        val rawRequest = adapter.deserialize(sin.nextLine())
        return rawRequest
    }*/
}