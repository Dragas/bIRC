package lt.saltyjuice.dragas.chatty.v3.core.route

abstract class RouteBuilder<Request, Response>
{
    protected open val mMiddlewares: ArrayList<Middleware<*, *>> = ArrayList()
    protected open var mCallback: ((Request) -> Response?)? = null
    protected open var mTestCallback: ((Request) -> Boolean)? = null

    open fun middleware(name: String): RouteBuilder<Request, Response>
    {
        val middleware = Middleware.getMiddleware(name)
        if (!mMiddlewares.contains(middleware))
            mMiddlewares.add(middleware)
        return this
    }

    open fun callback(callback: (Request) -> Response?): RouteBuilder<Request, Response>
    {
        this.mCallback = callback
        return this
    }

    open fun testCallback(callback: (Request) -> Boolean): RouteBuilder<Request, Response>
    {
        this.mTestCallback = callback
        return this
    }

    abstract fun build(): Route<Request, Response>
}