package lt.saltyjuice.dragas.chatty.v3.biscord

import lt.saltyjuice.dragas.chatty.v3.biscord.controller.CardController
import lt.saltyjuice.dragas.chatty.v3.biscord.controller.DeckController
import lt.saltyjuice.dragas.chatty.v3.biscord.entity.PlayerClass
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DeckControllerTest
{
    @Test
    fun canDecodeCards()
    {
        var count = 0
        dc.deck.forEach {
            count += it.value
        }
        Assert.assertEquals(30, count)
    }

    @Test
    fun canDecodeClass()
    {
        Assert.assertEquals("${dc.heroId}", PlayerClass.Druid, dc.heroClass)
    }

    @Test
    fun canDecodeVersion()
    {
        Assert.assertEquals(1, dc.version)
    }

    @Test
    fun canDecodeFormat()
    {
        Assert.assertEquals(DeckController.Format.Standard, dc.format)
    }

    @Test
    fun canDecodeNumberOfHeroes()
    {
        Assert.assertEquals(1, dc.numberOfHeroes)
    }

    companion object
    {
        @JvmStatic
        lateinit var dc: DeckController

        @JvmStatic
        val hash = "AAECAZICBvgMrqsClL0C+cACyccCmdMCDEBf/gHEBuQIvq4CtLsCy7wCz7wC3b4CoM0Ch84CAA=="

        @BeforeClass
        @JvmStatic
        fun init()
        {
            dc = DeckController()
            CardController()
            Assert.assertTrue(dc.canDecode(hash))
            //dc.initializeDecoder()
            dc.decodeAsDeck()
        }
    }
}