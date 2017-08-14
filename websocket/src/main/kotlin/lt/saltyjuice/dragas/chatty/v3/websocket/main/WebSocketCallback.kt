package lt.saltyjuice.dragas.chatty.v3.websocket.main

/**
 * A workaround for tyrus inability to use multiple decoders for possible multiple types that might be returned from
 * session being decoded.
 *
 * These callbacks are meant for single parameter lambdas, which are equivalent to message handlers in Tyrus endpoint.
 *
 * The testing for [canBeCalled] works in two layers. First it checks whether or not the class provided in this wrapper
 * is a super class to calling object. This means that the callback can be called partially - assuming that object is that of inheriting class.
 * The second layer is testing whether or not the provided objects class is super class to the one provided here. The only way that
 * test returns true is if the provided class is the same as argument class. Then by intersecting the two results [canBeCalled] returns true,
 * if this callback can be called fully.
 *
 * WebSocketEndpoint implementation prefers callbacks that can be called fully using provided class.
 */
open class WebSocketCallback<T, R>(private val clazz: Class<T>, private val callback: ((T) -> R))
{

    /**
     * Equivalent to calling canBeCalledPartially(any.javaClass)
     */
    open fun canBeCalledPartially(any: Any): Boolean
    {
        return canBeCalledPartially(any.javaClass)
    }

    /**
     * Tests whether or not the provided class here is a super class to [clazz]
     */
    open fun canBeCalledPartially(clazz: Class<*>): Boolean
    {
        return this.clazz.isAssignableFrom(clazz)
    }

    /**
     * Tests whether or not provided class can be used fully when calling this callback
     */
    open fun canBeCalled(clazz: Class<*>): Boolean
    {
        return clazz.isAssignableFrom(this.clazz) && canBeCalledPartially(clazz)
    }

    /**
     * Equvalent to calling canBeCalled[any.javaClass]
     */
    open fun canBeCalled(any: Any): Boolean
    {
        return canBeCalled(any.javaClass)
    }

    /**
     * Calls the provided callback method with given argument and returns the result.
     *
     * If a method should return null or nothing at all, you should specify R as Unit.
     */
    open fun call(any: T): R
    {
        return callback.invoke(any)
    }

}