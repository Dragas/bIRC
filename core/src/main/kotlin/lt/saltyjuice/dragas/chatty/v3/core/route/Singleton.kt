package lt.saltyjuice.dragas.chatty.v3.core.route


/**
 * Marks that particular controller is a singleton and should not be recreated for every request.
 */
@Target(AnnotationTarget.CLASS)
annotation class Singleton()