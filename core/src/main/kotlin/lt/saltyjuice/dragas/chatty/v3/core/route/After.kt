package lt.saltyjuice.dragas.chatty.v3.core.route

import lt.saltyjuice.dragas.chatty.v3.core.middleware.AfterMiddleware
import kotlin.reflect.KClass


/**
 * Marks that particular callback or controller or client uses mentioned middleware before using that generated response.
 *
 * Behaves like [Before].
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention()
annotation class After(vararg val value: KClass<out AfterMiddleware<*>>)
