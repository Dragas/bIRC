package lt.saltyjuice.dragas.chatty.v3.biscord.entity

enum class PlayerClass(val value: String, val color: Int)
{
    Mage("Mage", 0x7882ac),
    Warlock("Warlock", 0x533b5b),
    Paladin("Paladin", 0xab682e),
    Warrior("Warrior", 0x621a21),
    Rogue("Rogue", 0x3f383c),
    Hunter("Hunter", 0x2e5e21),
    Priest("Priest", 0xd2d9de),
    Shaman("Shaman", 0x2f3461),
    Druid("Druid", 0x713b24),
    Neutral("Neutral", 0x7a6a5d)
}