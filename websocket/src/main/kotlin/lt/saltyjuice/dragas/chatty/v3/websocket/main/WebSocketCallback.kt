package lt.saltyjuice.dragas.chatty.v3.websocket.main

/**
 * A workaround for tyrus inability to use multiple decoders for possible multiple types that might be returned from
 * session being decoded.
 */
open class WebSocketCallback<T, R>(private val clazz: Class<T>, private val callback: ((T) -> R))
{

    open fun canBeCalledPartially(any: Any): Boolean
    {
        return canBeCalledPartially(any.javaClass)
    }

    open fun canBeCalledPartially(clazz: Class<*>): Boolean
    {
        return this.clazz.isAssignableFrom(clazz)
    }

    open fun canBeCalled(clazz: Class<*>): Boolean
    {
        return clazz.isAssignableFrom(this.clazz) && canBeCalledPartially(clazz)
    }

    open fun canBeCalled(any: Any): Boolean
    {
        return canBeCalled(any.javaClass)
    }

    open fun call(any: T): R
    {
        return callback.invoke(any)
    }

}