package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.biscord.controller.CardController
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Message
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CardControllerTest
{
    @Test
    fun isCardRequest()
    {
        val messageCreateEvent = EventMessageCreate()
        messageCreateEvent.data = Message().apply()
        {
            content = "card wrath"
        }
        Assert.assertTrue(cc.isCardRequest(messageCreateEvent))
    }

    @Test
    fun getsCardExactly()
    {
        cc.pushArguments(arrayOf("wrath"))
        cc.getFilteredForSingle(CardController.getCards().parallelStream()).findFirst().get()
    }

    @Test
    fun getsException()
    {
        cc.pushArguments(arrayOf("black lotus"))
        cc.getFilteredForSingle(CardController.getCards().parallelStream()).findFirst().get()
    }

    @Test
    fun getsManyCards()
    {
        cc.pushArguments(arrayOf("murloc"))
        Assert.assertTrue(cc.getFilteredForMany(CardController.getCards().parallelStream()).count() > 0)
    }

    @Test
    fun fallsBackToMany()
    {
        cc.pushArguments(arrayOf("murloc"))
        Assert.assertTrue(cc.filterCards().isNotEmpty())
    }


    companion object
    {
        @JvmStatic
        private lateinit var cc: CardController

        @JvmStatic
        @BeforeClass
        fun init()
        {
            cc = CardController()
        }
    }
}