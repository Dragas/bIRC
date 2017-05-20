# bIRC #

Somewhat elegant Java/Kotlin IRC bot framework.

### Adding functionality ###

bIRC was written with modular functionality in mind, thus adding responses 
is just as easy as creating your own class. Literally.

First you have to create a class which extends `Route`.

```
class RespondToHello extends Route
{
    MyRoute(String commandWord) // note, your constructor needs to call super with route's trigger word.
    {
        super(commandWord);
    }
}
```
Note: depending on your preference, you can either write your trigger word 
here or in Routes.kt file. This particular example assumes that you want 
the latter.

Then you have to override its `Response.ToServer onTrigger(Request request)` 
method.

```
Response.ToServer onTrigger(Request request)
{
    return new Response(request.getTarget(), "HELLO WORLD");
}
```

Finally you register your route in `Routes.kt` file by adding it into 
your or a predefined `RouteGroup`.

```
fun initializeRoutes(): Array<Route.RouteGroup> {
    val array = ArrayList<Route.RouteGroup>()
    array.add(ServerRouteGroup(Pong()))
    array.add(CommonRouteGroup(RespondToHello("hello"))
    return array.toTypedArray()
}
```
That's it!

Depending on preference, you might want to override `type` field in your 
route to respond to either private messages, channel messages or both (with type `NONE`).