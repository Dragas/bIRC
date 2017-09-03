package lt.saltyjuice.dragas.chatty.v3.biscord.utility

import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
import retrofit2.Call
import retrofit2.http.GET

interface CardAPI
{
    /**
     * Returns many cards by partial name.
     */
    @GET("cards.json")
    fun getCards(): Call<Set<Card>>

    //https://api.hearthstonejson.com/v1/latest/enUS/
}