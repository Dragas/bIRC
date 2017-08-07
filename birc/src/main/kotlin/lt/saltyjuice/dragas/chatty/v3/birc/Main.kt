@file:JvmName("MainKt")

package lt.saltyjuice.dragas.chatty.v3.birc

import com.google.gson.Gson
import kotlinx.coroutines.experimental.runBlocking
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader


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

fun getReader(filename: String): Reader
{
    val classLoader = Thread.currentThread().contextClassLoader
    val stream = classLoader.getResourceAsStream(filename)
    val fileReader = InputStreamReader(stream)
    //val fileReader = FileReader(filename)
    return fileReader
}