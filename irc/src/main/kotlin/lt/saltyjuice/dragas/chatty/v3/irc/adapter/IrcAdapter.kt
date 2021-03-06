package lt.saltyjuice.dragas.chatty.v3.irc.adapter

import lt.saltyjuice.dragas.chatty.v3.core.adapter.Adapter
import lt.saltyjuice.dragas.chatty.v3.irc.message.Request
import lt.saltyjuice.dragas.chatty.v3.irc.message.Response

/**
 * IRC message adapter.
 *
 * Should be implemented in accordance to RFC2812 section 2.3 and be able to deserialize
 * [ ":" prefix SPACE ] command [ params ] crlf syntax messages.
 */
open class IrcAdapter : Adapter<String, Request, Response, String>()
{
    override fun serialize(any: Response): String
    {
        val sb = StringBuilder()
        sb.append(any.command)
        any.arguments.forEach {
            sb.append(" $it")
        }
        if (!sb.isBlank())
            sb.append("\r\n")
        val serializedChildren = any.otherResponses.
                parallelStream()
                .map(this::serialize)
                .filter {
                    it.isNotEmpty()
                }
                .collect(
                        {
                            StringBuilder()
                        },
                        { supplier: StringBuilder, element: String ->
                            supplier.append(element)
                        },
                        { supplier: StringBuilder, element: StringBuilder ->
                            supplier.append(element.toString())
                        })
        sb.append(serializedChildren)
        return sb.toString()
    }

    override fun deserialize(block: String): Request
    {
        val request = Request(block)
        val metadata = splitMetaData(block)
        request.prefixes = extractPrefix(metadata[0])
        request.arguments = extractArguments(metadata[2])
        request.command = metadata[1]
        return request
    }

    /**
     * Splits message data according to [ ":" prefix SPACE ] command [ params ] syntax.
     *
     * The main idea here is that optional groups are separated with spaces (RFC 1459 section 2.3.1 and
     * RFC 2812 section 2.3.1).
     *
     * @param block message provided by server to be decoded.
     * @return a list which is split in up to 3 groups: prefix, command and params. Particular spots are empty strings, when unavailable.
     */
    protected open fun splitMetaData(block: String): List<String>
    {
        //There seems to be a discrepancy between RFC 1459 and RFC 2812, where there's an additional
        // *14 symbol in parameters definition. The inverse of this (14*) would be repetition which
        // ISO 14977 states. Currently this method falls back to RFC 1459 definition.
        val split = block.split(" ", ignoreCase = true, limit = 2) // splits splits into prefix and (command [params]) or command [params]
        val nullableList = ArrayList<String>(split)
        if (split[0].startsWith(":"))
        {
            nullableList.remove(split[1])
            nullableList.addAll(split[1].split(" ", limit = 2))
        }
        if (nullableList.size < 3)
        {
            //It is possible that there's only command available, thus padding must be added
            nullableList.add(0, "")
            if (nullableList.size < 3) // padding for arguments
                nullableList.add("")
        }
        return nullableList
    }

    /**
     * In accordance to RFC 2812 section 2.3.1 prefix syntax is "servername / ( nickname [ [ "!" user ] "@" host ] )",
     *  which is part of optional group [ ":" prefix SPACE ]
     * therefore this particular method tries to extract the prefix part from metadata.
     *
     * This part is used to determine origin of request in question.
     * @param prefixContainer a block of string extracted when splitting metadata from actual parameters
     * @return a list of strings, with up to 3 parameters:
     * * First one is either nickname/servername. Only available if [prefixContainer] starts with ":" (colon character)
     * * Second parameter is user. Only available if prefix isn't only server name and "!" (exclamation mark)
     * character is available in [prefixContainer].
     * * Third parameter is hostname. Only available if [prefixContainer] contains "@" (at sign) character.
     *
     * If a parameter is unavailable, querying the list returns an empty string for that parameter.
     */
    protected open fun extractPrefix(prefixContainer: String): List<String>
    {
        val extractedData = ArrayList<String>(3)
        extractedData.addAll(arrayOf("", "", ""))
        // prefix must start with ":" as it is part of the optional,
        // group. If it doesn't, assume that there's no prefix.
        if (!prefixContainer.startsWith(":"))
            return extractedData
        val exclamationIndex = prefixContainer.indexOf("!")
        val atIndex = prefixContainer.indexOf("@")
        //since @ will always be available it has to be checked for first
        if (atIndex == -1)
        {
            extractedData[0] = prefixContainer // means that prefix is only hostname
        }                                                       // also replaces start of line
        else
        {
            val nicknameAndUser = prefixContainer.substring(0, atIndex)
            if (exclamationIndex != -1)
            {
                extractedData[0] = nicknameAndUser.substring(0, exclamationIndex)
                extractedData[1] = nicknameAndUser.substring(exclamationIndex + 1)
            }
            extractedData[2] = prefixContainer.substring(atIndex + 1)
        }
        extractedData[0] = extractedData[0].replace(":", "")
        return extractedData
    }

    /**
     * Extracts arguments from message according to RFC 2813 syntax:
     * *14( SPACE middle ) [ SPACE ":" trailing ] / 14( SPACE middle ) [ SPACE [ ":" ] trailing ]
     *
     * @param arguments a 3rd returned value from [splitMetaData]
     * @return a list of arguments split by " "
     */
    protected open fun extractArguments(arguments: String): List<String>
    {
        val separateLongArgument = arguments.split(Regex(" ?:"), 2) // always returns 2, even if one of them is empty
        val actualArguments = separateLongArgument[0].split(" ")
        val returnable = ArrayList(actualArguments)
        if (separateLongArgument.size > 1)
            returnable.add(separateLongArgument[1])
        returnable.removeAll { it.isEmpty() }
        return returnable
    }
}