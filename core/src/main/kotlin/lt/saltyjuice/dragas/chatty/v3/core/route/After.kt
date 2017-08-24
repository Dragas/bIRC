package lt.saltyjuice.dragas.chatty.v3.core.route

import kotlin.reflect.KClass


/**
 * Marks that particular callback or controller or client uses mentioned middleware before using that generated response.
 */
@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention()
annotation class After(vararg val value: KClass<*>)
