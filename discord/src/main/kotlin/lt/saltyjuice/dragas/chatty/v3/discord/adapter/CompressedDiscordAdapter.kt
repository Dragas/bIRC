package lt.saltyjuice.dragas.chatty.v3.discord.adapter

import lt.saltyjuice.dragas.chatty.v3.discord.message.request.OPRequest
import java.io.InputStream
import java.util.*
import java.util.zip.Inflater
import java.util.zip.InflaterInputStream
import javax.websocket.Decoder
import javax.websocket.EndpointConfig

open class CompressedDiscordAdapter : Decoder.BinaryStream<OPRequest<*>>
{
    protected open val adapter: DiscordAdapter by lazy()
    {
        DiscordAdapter.getInstance()
    }

    /**
     * Decode the given bytes read from the input stream into an object of type T.
     *
     * @param is the input stream carrying the bytes.
     * @return the decoded object.
     */
    override fun decode(bis: InputStream): OPRequest<*>
    {
        return adapter.decode(getAsString(bis))
    }

    open fun getAsString(bis: InputStream): String
    {
        val inflater = Inflater()
        val iis = InflaterInputStream(bis, inflater)
        val sis = Scanner(iis)
        val sb = StringBuilder()
        while (sis.hasNextLine())
        {
            sb.append(sis.nextLine())
        }
        iis.close()
        sis.close()
        inflater.end()
        return sb.toString()
    }

    /**
     * This method is called with the endpoint configuration object of the
     * endpoint this decoder is intended for when
     * it is about to be brought into service.
     *
     * @param config the endpoint configuration object when being brought into
     * service
     */
    override fun init(config: EndpointConfig?)
    {

    }

    /**
     * This method is called when the decoder is about to be removed
     * from service in order that any resources the encoder used may
     * be closed gracefully.
     */
    override fun destroy()
    {

    }
}