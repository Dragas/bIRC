package lt.dragas.birc.v3.core.route


abstract class Controller<T, R>
{
    abstract fun onTrigger(request: T): R?
}