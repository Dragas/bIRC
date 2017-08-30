package lt.saltyjuice.dragas.chatty.v3.core.main

object Utility
{
    /**
     * Copies the contents of the list and cleans the source.
     */
    fun <R> copyAndClean(list: MutableList<R>): List<R>
    {
        val returnable = list.toList()
        list.clear()
        return returnable
    }
}