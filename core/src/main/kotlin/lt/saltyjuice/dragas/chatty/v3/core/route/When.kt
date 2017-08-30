package lt.saltyjuice.dragas.chatty.v3.core.route


/**
 * Marks that this method is tested by another method in this class. Case sensitive.
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
annotation class When(val value: String)