/*
fun main(args: Array<String>)
{
    val file = File("../../../aa")
    val fin = Scanner(file)
    val outputfile = File("enumerated.kt")
    outputfile.createNewFile()
    val fout = PrintWriter(outputfile)
    while (fin.hasNextLine())
    {
        val line = fin.nextLine().split("\t")
        val name = line[0]
        val type = line[1]
        val what = line[2]
        val description = line[3]
        val sb = StringBuilder(template)
        sb.replace(Regex.fromLiteral("{clazz}"), what.capitalize())
        */
/*sb.replace(Regex.fromLiteral("{description}"), description)
        sb.replace(Regex.fromLiteral("{affects}"), type)
        sb.replace(Regex.fromLiteral("{type}"), what)

        sb.replace(Regex.fromLiteral("{value}"), name)*//*

        sb.replace(Regex.fromLiteral("{name}"), name.toUpperCase())
        */
/*if(fin.hasNextLine())
            sb.append(",")*//*

        sb.appendln()
        sb.appendln()
        fout.print(sb.toString())
    }
    fout.close()
    fin.close()
}

fun StringBuilder.replace(regex: Regex, target: String)
{
    var result = regex.find(this)
    while (result != null)
    {
        val first = result.range.first
        val last = result.range.endInclusive + 1
        this.replace(first, last, target)
        result = result.next()
    }
}

val template = """AuditLogChangeKey.{name} -> {
    |returnable.old = context.deserialize<{clazz}>(pojo["old"], {clazz}::class.java)
    |returnable.new = context.deserialize<{clazz}>(pojo["new"], {clazz}::class.java)
    |}
""".trimMargin()

class Enum()
{
    var name: String = ""
    var type: String = ""
    var what: String = ""
    var description: String = ""
}*/


/*fun main(args: Array<String>)
{
    *//*val arguments = "Leeroy Jenkins --verbose --gold -n"
    println(arguments.split(Regex("-{1,2}")))*//*
}*/

/*fun main(args : Array<String>)
{
    val a  = 10
    val b = (a shl 7).toByte()
}*/

fun main(args: Array<String>)
{
    /*val deckstring = "AAECAZICBvgMrqsClL0C+cACyccCmdMCDEBf/gHEBuQIvq4CtLsCy7wCz7wC3b4CoM0Ch84CAA=="
    val decoded = Base64.getDecoder().decode(deckstring)
    val bais = ByteArrayInputStream(decoded)
    val din = DataInputStream(bais)
    println("initial byte: ${din.read()}")
    println("encoding version: ${din.read()}")
    println("format: ${din.read()} (1 => wild, 2 => standard)")
    println("number of heroes: ${din.read()}")
    var heroId = din.read()
    println("hero id: $heroId")*/
    //println("initial byte: ${din.readByte()}")

}