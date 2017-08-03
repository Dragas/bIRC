@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.birc

import kotlinx.coroutines.experimental.runBlocking
import java.io.FileReader
import java.io.IOException


fun main(args: Array<String>) = runBlocking<Unit>
{
    val settings = BIrcSettings()
    val client = BIrcClient(settings)
    client.initialize()
    client.connect()
    if (client.isConnected())
        client.onConnect()
    try // sockets usually disconnect by throwing IOException.
    {
        while (client.isConnected())
        {
            client.run()
        }
    }
    catch(exception: IOException)
    {
        println(exception)
        exception.printStackTrace()
    }
    finally
    {
        client.onDisconnect()
    }
}

fun getReader(): FileReader
{
    val fileReader = FileReader("settings.json")
    return fileReader
}