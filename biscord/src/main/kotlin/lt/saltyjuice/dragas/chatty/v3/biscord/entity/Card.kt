package lt.saltyjuice.dragas.chatty.v3.biscord.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Embed
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Field
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Image

open class Card : Comparable<Card>
{
    /**
     * Compares this object with the specified object for order. Returns zero if this object is equal
     * to the specified [other] object, a negative number if it's less than [other], or a positive number
     * if it's greater than [other].
     */
    override fun compareTo(other: Card): Int
    {
        return dbfId - other.dbfId
    }

    @Expose
    @SerializedName("id")
    var cardId: String = ""
    @Expose
    @SerializedName("dbfId")
    var dbfId: Int = -1
    @Expose
    @SerializedName("name")
    var name: String = ""
    @Expose
    @SerializedName("cardSet")
    var cardSet: String = ""
    @Expose
    @SerializedName("type")
    var type: Type? = Type.Spell
    @Expose
    @SerializedName("faction")
    var faction: String = ""
    @Expose
    @SerializedName("rarity")
    var rarity: String = ""
    @Expose
    @SerializedName("cost")
    var cost: Int = 0
    @Expose
    @SerializedName("attack")
    var attack: Int = 0
    @Expose
    @SerializedName("health")
    var health: Int = 0
    @Expose
    @SerializedName("armor")
    var armor: Int = 0
    @Expose
    @SerializedName("text")
    var text: String = ""
    @Expose
    @SerializedName("flavor")
    var flavor: String = ""
    @Expose
    @SerializedName("entourage")
    var entourage: ArrayList<String> = ArrayList()
    var entourages: ArrayList<Card> = ArrayList()
    @Expose
    @SerializedName("artist")
    var artist: String = ""
    @Expose
    @SerializedName("collectible")
    var collectible: Boolean = false
    @Expose
    @SerializedName("elite")
    var elite: String = ""
    @Expose
    @SerializedName("race")
    var race: String = ""
    @Expose
    @SerializedName("playerClass")
    var playerClass: PlayerClass? = PlayerClass.Neutral
    @Expose
    @SerializedName("img")
    var img: String = ""
        get()
        {
            return "https://art.hearthstonejson.com/v1/render/latest/enUS/256x/$cardId.png"
        }
    @Expose
    @SerializedName("imgGold")
    var imgGold: String = ""
    @Expose
    @SerializedName("locale")
    var locale: String = ""
    @Expose
    @SerializedName("howToGet")
    var howToGet: String = ""
    @Expose
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