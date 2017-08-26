package lt.saltyjuice.dragas.chatty.v3.discord

import lt.saltyjuice.dragas.chatty.v3.discord.api.Utility

interface CloneableData : Cloneable
{
    override public fun clone(): CloneableData
    {
        val json = Utility.gson.toJson(this)
        return Utility.gson.fromJson(json, this::class.java)
    }
}