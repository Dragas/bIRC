package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.main.Client
import java.lang.reflect.Method

/**
 * Base class for controllers, that can be
 */
abstract class Controller<Request, Response>
{
    /**
     * Filters this class for methods that contain [On] annotation
     */
    fun methods(): List<Method>
    {
        val allMethods = this.javaClass.methods
        val filteredMethods = allMethods.filter {
            it.getAnnotation(On::class.java) != null
        }

        if (filteredMethods.isEmpty())
            throw IllegalStateException("Controller should have atleast 1 @On callback")
        return filteredMethods
    }

    /**
     * Permits writing response to client from controller.
     */
    open fun writeResponse(response: Response)
    {
        val client = Client.getInstance() as Client<String, Request, Response, String>
        client.writeResponse(response)
    }
}