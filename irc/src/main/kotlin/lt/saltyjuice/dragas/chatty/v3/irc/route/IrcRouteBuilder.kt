package lt.saltyjuice.dragas.chatty.v3.irc.route

import lt.saltyjuice.dragas.chatty.v3.core.route.Middleware
import lt.saltyjuice.dragas.chatty.v3.core.route.RouteBuilder
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response
import java.util.regex.Pattern


/**
 * IRC Route builder.
 *
 * A helper class that simplifies building routes for your controllers. Usually routes just need a [testCallback] and [callback]
 */
open class IrcRouteBuilder : RouteBuilder<Request, Response>()
{
    protected open var mType: String? = null
    override val mMiddlewares: ArrayList<Middleware<*, *>> = ArrayList()
    override var mCallback: ((Request) -> Response?)? = null
    override var mTestCallback: ((Request) -> Boolean)? = { true }

    override fun middleware(name: String): IrcRouteBuilder
    {
        return super.middleware(name) as IrcRouteBuilder
    }

    override fun callback(callback: (Request) -> Response?): IrcRouteBuilder
    {
        return super.callback(callback) as IrcRouteBuilder
    }

    override fun testCallback(callback: (Request) -> Boolean): IrcRouteBuilder
    {
        return super.testCallback(callback) as IrcRouteBuilder
    }

    open fun type(command: Command): IrcRouteBuilder
    {
        return type(command.value)
    }

    open fun type(command: String): IrcRouteBuilder
    {
        mType = command
        return this
    }

    open fun testCallback(pattern: String): IrcRouteBuilder
    {
        mTestCallback = { request -> Pattern.compile(pattern).matcher(request.arguments.last()).find() }
        return this
    }

    override fun build(): IrcRoute
    {
        return object : IrcRoute()
        {
            override var type: String = this@IrcRouteBuilder.mType ?: throw Exception("Routes need to have a type. See Command enums")
                set(value)
                {
                }
            override var middlewares: List<Middleware<Request, Response>> = this@IrcRouteBuilder.mMiddlewares as List<Middleware<Request, Response>>
            override var testCallback: (Request) -> Boolean = this@IrcRouteBuilder.mTestCallback ?: throw Exception("Route should have a test callback")
                set(value)
                {
                }
            override var callback: (Request) -> Response? = this@IrcRouteBuilder.mCallback ?: throw Exception("Route should have a callback")
                set(value)
                {
                }
        }
    }
}