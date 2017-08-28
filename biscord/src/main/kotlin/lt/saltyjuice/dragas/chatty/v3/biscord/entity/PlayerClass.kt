package lt.saltyjuice.dragas.chatty.v3.biscord.entity

enum class PlayerClass(val value: String, val color: Int, val id: Int)
{
    Mage("Mage", 0x7882ac, 637),
    Warlock("Warlock", 0x533b5b, 893),
    Paladin("Paladin", 0xab682e, 671),
    Warrior("Warrior", 0x621a21, 7),
    Rogue("Rogue", 0x3f383c, 930),
    Hunter("Hunter", 0x2e5e21, 31),
    Priest("Priest", 0xd2d9de, 813),
    Shaman("Shaman", 0x2f3461, 1066),
    Druid("Druid", 0x713b24, 274),
    Neutral("Neutral", 0x7a6a5d, -1);

    companion object
    {
        @JvmStatic
        fun getById(id: Int): PlayerClass
        {
            return PlayerClass.values().find { it.id == id } ?: Neutral
        }
    }
}