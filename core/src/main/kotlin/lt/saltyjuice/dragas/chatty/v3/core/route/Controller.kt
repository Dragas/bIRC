package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import java.lang.reflect.Method

/**
 * Base class for controllers, that can be
 */
abstract class Controller<Request, Response>(private val client: Client<*, Request, Response, *>)
{
    /**
     * Filters this class for methods that contain [Callback] annotation
     */
    fun methods(): List<Method>
    {
        val allMethods = this.javaClass.methods
        val filteredMethods = allMethods.filter {
            it.getAnnotation(Callback::class.java) != null
        }

        if (filteredMethods.isEmpty())
            throw IllegalStateException("Controller should have atleast 1 @Callback")
        return filteredMethods
    }

    /**
     * Permits writing response to client from controller.
     */
    fun writeResponse(response: Response)
    {
        client.writeResponse(response)
    }
}