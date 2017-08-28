package lt.saltyjuice.dragas.chatty.v3.biscord.controller

import lt.saltyjuice.dragas.chatty.v3.biscord.Settings
import lt.saltyjuice.dragas.chatty.v3.core.route.On
import lt.saltyjuice.dragas.chatty.v3.core.route.When
import lt.saltyjuice.dragas.chatty.v3.discord.controller.DiscordController
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.event.EventMessageCreate
import lt.saltyjuice.dragas.chatty.v3.discord.message.response.OPResponse
import org.jsoup.Jsoup

class AmazonController : DiscordController()
{
    init
    {
        if (Settings.MASHAPE_KEY != null)
        {
            val document = Jsoup.connect("https://www.amazon.com/Amazon-500-Coins/dp/B018HB6E80?th=1").get()
            val reducedPriceNode = document.getElementById("priceblock_ourprice")
            val builder = MessageBuilder()
                    .appendLine("Your daily favor is here!")
            if (reducedPriceNode != null)
            {
                val reducedPrice = reducedPriceNode.text()
                try
                {
                    val actualPrice = reducedPriceNode.parent().parent().firstElementSibling().child(1).child(0).text()
                    builder
                            .appendLine("Default price: $actualPrice")
                            .appendLine("Current price: $reducedPrice")
                            .appendLine("Enjoy!")
                    builder.send("351806091958681611")
                }
                catch (err: Throwable)
                {
                    err.printStackTrace(System.err)
                }
            }
            else
            {
                builder
                        .appendLine("Sadly, I was unable to parse the data. Notify Dragas about it ;_;")
                        .appendLine("Note: What might have happened is that there are no offers right now.")
                        .send("351806091958681611")
            }
        }

    }

    @On(EventMessageCreate::class)
    @When("falsyMethod")
    fun onMessage(request: EventMessageCreate): OPResponse<*>?
    {
        return null
    }

    fun falsyMethod(request: EventMessageCreate): Boolean
    {
        return false
    }
}