package lt.saltyjuice.dragas.chatty.v3.discord

import lt.saltyjuice.dragas.chatty.v3.discord.exception.MessageBuilderException
import lt.saltyjuice.dragas.chatty.v3.discord.message.MessageBuilder
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.Embed
import lt.saltyjuice.dragas.chatty.v3.discord.message.general.User
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MessageBuilderTest
{
    private var builder: MessageBuilder = MessageBuilder()

    @Before
    fun initBuilder()
    {
        builder = MessageBuilder()
    }

    @Test
    fun throwsWhenMessageIsTooLong()
    {
        testIfThrows()
        {
            builder.appendLine("".padEnd(Settings.MAX_MESSAGE_CONTENT_LENGTH + 1))
        }
    }

    @Test
    fun throwsWhenTryingToAddDuringNewLine()
    {
        testIfThrows()
        {
            builder.mentionStart(MessageBuilder.MentionType.ROLE)
            builder.appendLine("heh, greetings")
        }
    }

    @Test
    fun buildsMentionsCorrectly()
    {
        builder.mention(User().apply { id = "123456" }, false)
        builder.buildMessage()
    }

    @Test
    fun testMaximumEmbedFields()
    {
        testIfThrows()
        {
            repeat(26)
            {
                builder.field("a", "b")
            }
        }
    }

    @Test
    fun throwsWhenTryingToUseIncorrectEmbed()
    {
        val embed = Embed()
        embed.description = "".padEnd(Settings.MAX_EMBED_CONTENT_LENGTH + 1)
        testIfThrows { builder.embed(embed) }
    }

    @Test
    fun throwsWhenEmbedIsOverLength()
    {
        testIfThrows { builder.description("".padEnd(Settings.MAX_EMBED_CONTENT_LENGTH + 1)) }
    }

    @Ignore
    fun testIfThrows(throwingLambda: (() -> Unit))
    {
        var throws = false
        try
        {
            throwingLambda()
        }
        catch (err: MessageBuilderException)
        {
            throws = true
        }

        Assert.assertTrue(throws)
    }
}