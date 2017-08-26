package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.biscord.entity.Card
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CardAPI
{
    /**
     * Returns card by name or ID. This may return more then one card if they share the same name. Loatheb returns both the card and the boss.
     */
    @GET("cards/{name}")
    fun getSingleCard(@Path("name") name: String): Call<ArrayList<Card>>

    /**
     * Returns many cards by partial name.
     */
    @GET("cards/search/{name}")
    fun getCards(@Path("name") name: String): Call<ArrayList<Card>>
}