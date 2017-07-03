@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.birc

import kotlinx.coroutines.experimental.runBlocking
import java.io.FileReader


fun main(args: Array<String>) = runBlocking<Unit>
{
    val settings = BIrcSettings()
    val client = BIrcClient(settings)
    client.initialize()
    client.connect()
    client.onConnect()
    while (client.isConnected())
    {
        client.run()
    }
    client.onDisconnect()
}

fun getReader(): FileReader
{
    val fileReader = FileReader("settings.json")
    return fileReader
}