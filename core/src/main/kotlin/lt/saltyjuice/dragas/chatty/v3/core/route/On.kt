package lt.saltyjuice.dragas.chatty.v3.core.route

import kotlin.reflect.KClass

/**
 * Marks that particular method in [Controller] is used as a callback when router gets [clazz] request..
 */
@MustBeDocumented
@Retention()
@Target(AnnotationTarget.FUNCTION)
annotation class On(val clazz: KClass<*>)