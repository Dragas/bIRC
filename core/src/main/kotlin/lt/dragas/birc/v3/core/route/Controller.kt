package lt.dragas.birc.v3.core.route


interface Controller<T, R>
{
    fun onTrigger(request: T): R?
}