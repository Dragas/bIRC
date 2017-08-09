# Chatty/IRC
An implementation of Chatty for IRC based networks.

## Downloading

Much like Chatty/Core, you need to add sonatype as one of your repositories.
```groovy
repositories {
    maven { url "https://oss.sonatype.org/content/repositories/releases/" }
}
```

And then link Chatty/IRC as your dependency.
```groovy
dependencies {
    compile "lt.saltyjuice.dragas:chatty-irc:1.3.0"
}
```

## Setting up

Chatty/IRC implementations should use `IrcSettings` model to set up settings before attempting to
connect to your server. Each field corresponds to parameter that's used during authentication when
connected to server.


## Adding hookers and blackjack

By default, Chatty/IRC client implementation already handles pinging the server,
joining the default channels and setting default nickname, etc. Adding functionality
prefers using dependency injection pattern and injecting router into your desired controller. Just
override `IrcClient.initialize` and do the following

```kotlin
fun initialize()
{
    super.initialize()
    Controller(router)
}
```

And then in that controller's constructor

```kotlin
router.add(router.builder().let {
    it.type(Command.PRIVMSG)
    it.testCallback("regex pattern to match")
    it.callback(this::responseGenerator)
})
```

Once it receives a private message `responseGenerator` is triggered in that controller. After consuming
the request it should return a `Response` object or null, if according to IRC standard, request does
 not need a response.

You shouldn't keep the reference to router object in your controller or add additional routes 
at runtime.