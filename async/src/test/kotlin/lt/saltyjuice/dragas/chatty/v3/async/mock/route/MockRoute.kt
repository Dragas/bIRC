package lt.saltyjuice.dragas.chatty.v3.async.mock.route

import kotlinx.coroutines.experimental.channels.SendChannel
import lt.saltyjuice.dragas.chatty.v3.async.mock.message.MockRequest
import lt.saltyjuice.dragas.chatty.v3.async.route.AsyncRoute
import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.middleware.BeforeMiddleware
import lt.saltyjuice.dragas.chatty.v3.core.route.Controller
import lt.saltyjuice.dragas.chatty.v3.core.route.Route
import java.lang.reflect.Method

open class MockRoute : AsyncRoute<MockRequest, Float>()
{
    open class Builder : AsyncRoute.Builder<MockRequest, Float>()
    {
        override fun returnableRoute(): MockRoute
        {
            return MockRoute()
        }

        override fun adapt(route: Route<MockRequest, Float>): MockRoute
        {
            return super.adapt(route) as MockRoute
        }

        override fun after(clazz: Class<out AfterMiddleware<Float>>): Builder
        {
            return super.after(clazz) as Builder
        }

        override fun before(clazz: Class<out BeforeMiddleware<MockRequest>>): Builder
        {
            return super.before(clazz) as Builder
        }

        override fun callback(callback: (Route<MockRequest, Float>, MockRequest) -> Unit): Builder
        {
            return super.callback(callback) as Builder
        }

        override fun consume(controller: Class<out Controller<Float>>, method: Method): Builder
        {
            return super.consume(controller, method) as Builder
        }

        override fun controller(clazz: Class<out Controller<Float>>): Builder
        {
            return super.controller(clazz) as Builder
        }

        override fun description(string: String): Builder
        {
            return super.description(string) as Builder
        }

        override fun testCallback(callback: (Route<MockRequest, Float>, MockRequest) -> Boolean): Builder
        {
            return super.testCallback(callback) as Builder
        }

        override fun responseChannel(channel: SendChannel<Float>): Builder
        {
            return super.responseChannel(channel) as Builder
        }
    }
}