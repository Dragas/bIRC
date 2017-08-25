package lt.saltyjuice.dragas.chatty.v3.core.route


/**
 * Denotes that this particular route has a description.
 */
@Retention
@MustBeDocumented
@Target(AnnotationTarget.FIELD)
annotation class Description(val value: String)