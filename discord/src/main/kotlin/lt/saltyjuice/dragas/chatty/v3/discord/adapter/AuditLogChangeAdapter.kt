package lt.saltyjuice.dragas.chatty.v3.discord.adapter

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import lt.saltyjuice.dragas.chatty.v3.discord.enumerated.AuditLogChangeKey
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.AuditLogChange
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Overwrite
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Role
import java.lang.reflect.Type

class AuditLogChangeAdapter : JsonDeserializer<AuditLogChange<*>>
{
    /**
     * Gson invokes this call-back method during deserialization when it encounters a field of the
     * specified type.
     *
     * In the implementation of this call-back method, you should consider invoking
     * [JsonDeserializationContext.deserialize] method to create objects
     * for any non-trivial field of the returned object. However, you should never invoke it on the
     * the same type passing `json` since that will cause an infinite loop (Gson will call your
     * call-back method again).
     *
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @return a deserialized object of the specified type typeOfT which is a subclass of `T`
     * @throws JsonParseException if json is not in the expected format of `typeofT`
     */
    @Throws(JsonParseException::class, IllegalArgumentException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): AuditLogChange<*>
    {
        val returnable = AuditLogChange<Any>()
        val pojo = json.asJsonObject
        returnable.key = AuditLogChangeKey.valueOf(pojo["key"].asString)

        val oldValue = pojo["old_value"]
        val newValue = pojo["new_value"]
        when (returnable.key)
        {
            AuditLogChangeKey.ICON_HASH ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.SPLASH_HASH ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.OWNER_ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.REGION ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.AFK_CHANNEL_ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.AFK_TIMEOUT ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.MFA_LEVEL ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.VERIFICATION_LEVEL ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.EXPLICIT_CONTENT_FILTER ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.DEFAULT_MESSAGE_NOTIFICATIONS ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.VANITY_URL_CODE ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.ADD ->
            {
                val typetoken = object : TypeToken<ArrayList<Role>>()
                {}.rawType
                returnable.oldValue = context.deserialize<ArrayList<Role>>(oldValue, typetoken)
                returnable.newValue = context.deserialize<ArrayList<Role>>(newValue, typetoken)
            }

            AuditLogChangeKey.REMOVE ->
            {
                val typetoken = object : TypeToken<ArrayList<Role>>()
                {}.rawType
                returnable.oldValue = context.deserialize<ArrayList<Role>>(oldValue, typetoken)
                returnable.newValue = context.deserialize<ArrayList<Role>>(newValue, typetoken)
            }

            AuditLogChangeKey.PRUNE_DELETE_DAYS ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.WIDGET_ENABLED ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.WIDGET_CHANNEL_ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.POSITION ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.TOPIC ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.BITRATE ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.PERMISSION_OVERWRITES ->
            {
                val typetoken = object : TypeToken<ArrayList<Overwrite>>()
                {}.rawType
                returnable.oldValue = context.deserialize<ArrayList<Overwrite>>(oldValue, typetoken)
                returnable.newValue = context.deserialize<ArrayList<Overwrite>>(newValue, typetoken)
            }

            AuditLogChangeKey.NSFW ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.APPLICATION_ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.PERMISSIONS ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.COLOR ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.HOIST ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.MENTIONABLE ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.ALLOW ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.DENY ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.CODE ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.CHANNEL_ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.INVITER_ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.MAX_USES ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.USES ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.MAX_AGE ->
            {
                returnable.oldValue = context.deserialize<Int>(oldValue, Int::class.java)
                returnable.newValue = context.deserialize<Int>(newValue, Int::class.java)
            }

            AuditLogChangeKey.TEMPORARY ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.DEAF ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.MUTE ->
            {
                returnable.oldValue = context.deserialize<Boolean>(oldValue, Boolean::class.java)
                returnable.newValue = context.deserialize<Boolean>(newValue, Boolean::class.java)
            }

            AuditLogChangeKey.NICK ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.AVATAR_HASH ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.ID ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }

            AuditLogChangeKey.TYPE ->
            {
                returnable.oldValue = context.deserialize<String>(oldValue, String::class.java)
                returnable.newValue = context.deserialize<String>(newValue, String::class.java)
            }
            else ->
            {
                throw IllegalStateException("Invalid log change key: ${pojo["key"]}")
            }
        }
        return returnable
    }
}