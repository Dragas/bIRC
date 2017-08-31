package lt.saltyjuice.dragas.chatty.v3.async.mock.route

import kotlinx.coroutines.experimental.channels.SendChannel
import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncRoute
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import java.lang.reflect.Method

open class MockRoute : AsyncRoute<Int, Int>()
{
    open class Builder : AsyncRoute.Builder<Int, Int>()
    {
        override fun returnableRoute(): MockRoute
        {
            return MockRoute()
        }

        override fun adapt(route: Route<Int, Int>): MockRoute
        {
            return super.adapt(route) as MockRoute
        }

        override fun after(clazz: Class<out AfterMiddleware<Int>>): Builder
        {
            return super.after(clazz) as Builder
        }

        override fun before(clazz: Class<out BeforeMiddleware<Int>>): Builder
        {
            return super.before(clazz) as Builder
        }

        override fun callback(callback: (Route<Int, Int>, Int) -> Unit): Builder
        {
            return super.callback(callback) as Builder
        }

        override fun consume(controller: Class<out Controller<Int>>, method: Method): Builder
        {
            return super.consume(controller, method) as Builder
        }

        override fun controller(clazz: Class<out Controller<Int>>): Builder
        {
            return super.controller(clazz) as Builder
        }

        override fun description(string: String): Builder
        {
            return super.description(string) as Builder
        }

        override fun testCallback(callback: (Route<Int, Int>, Int) -> Boolean): Builder
        {
            return super.testCallback(callback) as Builder
        }

        override fun responseChannel(channel: SendChannel<Int>): Builder
        {
            return super.responseChannel(channel) as Builder
        }
    }
}