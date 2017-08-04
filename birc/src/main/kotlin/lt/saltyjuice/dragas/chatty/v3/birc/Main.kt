@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.birc

import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import java.io.FileReader
import java.io.IOException


fun main(args: Array<String>) = runBlocking<Unit>
{
    val gson = Gson()
    val settings = gson.fromJson<BIrcSettings>(getReader("settings.json"), BIrcSettings::class.java)
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

fun getReader(filename: String): FileReader
{
    val classLoader = Thread.currentThread().contextClassLoader
    val fileReader = FileReader(classLoader.getResource(filename).file)
    return fileReader
}