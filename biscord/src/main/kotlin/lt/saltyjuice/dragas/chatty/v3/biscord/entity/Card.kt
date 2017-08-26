package lt.saltyjuice.dragas.chatty.v3.biscord.entity

import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Embed
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Field
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Image

open class Card
{
    @SerializedName("cardId")
    var cardId: String = ""
    @SerializedName("dbfId")
    var dbfId: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("cardSet")
    var cardSet: String = ""
    @SerializedName("type")
    var type: Type? = Type.Spell
    @SerializedName("faction")
    var faction: String = ""
    @SerializedName("rarity")
    var rarity: String = ""
    @SerializedName("cost")
    var cost: Int = 0
    @SerializedName("attack")
    var attack: Int = 0
    @SerializedName("health")
    var health: Int = 0
    @SerializedName("armor")
    var armor: Int = 0
    @SerializedName("text")
    var text: String = ""
    @SerializedName("flavor")
    var flavor: String = ""
    @SerializedName("artist")
    var artist: String = ""
    @SerializedName("collectible")
    var collectible: Boolean = true
    @SerializedName("elite")
    var elite: String = ""
    @SerializedName("race")
    var race: String = ""
    @SerializedName("playerClass")
    var playerClass: PlayerClass? = PlayerClass.Neutral
    @SerializedName("img")
    var img: String = ""
    @SerializedName("imgGold")
    var imgGold: String = ""
    @SerializedName("locale")
    var locale: String = ""
    @SerializedName("howToGet")
    var howToGet: String = ""
    @SerializedName("howToGetGold")
    var howToGetGold: String = ""

    @JvmOverloads
    fun toEmbed(gold: Boolean = false): Embed
    {
        return Embed().apply()
        {
            title = name
            color = playerClass?.color ?: PlayerClass.Neutral.color
            fields = getCardFields()
            description = text
            image = Image().apply()
            {
                url = if (gold) imgGold else img
            }
        }
    }

    fun getCardFields(): ArrayList<Field>
    {
        val list = ArrayList<Field>()
        list.add(Field("Set", cardSet))
        if (rarity.isNotBlank())
            list.add(Field("Rarity", rarity))
        if (playerClass != null)
            list.add(Field("Class", playerClass!!.value))
        list.add(Field("Cost", "$cost"))
        when (type)
        {
            Type.Spell ->
            {

            }
            Type.Hero ->
            {
                list.add(Field("Armor", "$armor"))
                list.add(Field("Health", "$health"))
            }
            Type.Minion ->
            {
                list.add(Field("Attack", "$attack"))
                list.add(Field("Health", "$health"))
            }
            else ->
            {

            }
        }
        if (howToGet.isNotBlank())
            list.add(Field("How to get", howToGet))
        if (howToGetGold.isNotBlank())
            list.add(Field("How to get gold", howToGet))
        if (flavor.isNotBlank())
            list.add(Field("Flavor", flavor))


        return ArrayList(list.filter { it.value.isNotBlank() })
    }
}