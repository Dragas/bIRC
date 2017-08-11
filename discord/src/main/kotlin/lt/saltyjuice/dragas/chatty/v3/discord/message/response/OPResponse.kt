package lt.saltyjuice.dragas.chatty.v3.discord.message.response

abstract class OPResponse<D>
{
    abstract val op: Int

    abstract var data: D
}