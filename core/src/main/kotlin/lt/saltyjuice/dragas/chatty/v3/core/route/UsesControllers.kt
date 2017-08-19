package lt.saltyjuice.dragas.chatty.v3.core.route


import kotlin.reflect.KClass


/**
 * Marks the client that it uses particular set of controllers.
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
annotation class UsesControllers(vararg val value: KClass<*>)