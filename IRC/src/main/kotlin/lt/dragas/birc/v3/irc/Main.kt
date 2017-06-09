package lt.dragas.birc.v3.irc

import lt.dragas.birc.v3.irc.route.IrcRouteGroup

private val routeMap: HashMap<String, ArrayList<IrcRouteGroup>> = HashMap()

fun main(args: Array<String>)
{

}

fun initializeRoutes(): HashMap<String, ArrayList<IrcRouteGroup>>
{

    return routeMap
}