# Chatty ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=master)

Somewhat elegant Java/Kotlin chat bot framework.

Includes both sources and implementation for IRCs

## Usage

Considering that `chatty-core` is just an abstraction set, you might want to look into either `chatty-irc` or
even `chatty-birc`.

With that out of the way, `chatty-core` contains 4 abstractions that should be implemented: 
 * An adapter, located in `lt.saltyjuice.dragas.chatty.v3.core.adapter`;
   * An adapter handles how the data is wrapped and unwrapped from something you get from socket or what ever you choose
   to use. Has 4 generic parameters to help you with what should be returned when doing both serialization and deserialization.
 * An `io` scheme, located in `lt.saltyjuice.dragas.chatty.v3.core.io`;
   * `Input`/`Output` handles how data is passed to an adapter. This is the layer you'd want to have your socket streams at.  
 * Client, located in `lt.saltyjuice.dragas.chatty.v3.core.main`;
   * Client in itself handles how the entire thing works. Usually it requests wrapped data from `Input` and then passes
   responses from `Router` to `Output`.
 * A router, located in `lt.saltyjuice.dragas.chatty.v3.core.route`;
   * This is where the magic happens. Router checks which routes pass their test against the provided request object.
   Then depending on implementation, you might want to either return first passing response or all of them.

## Downloading 

Make sure you have sonatype as one of your checked repositories
```groovy
repositories {
    mavenCentral()
    jcenter()
    maven { url "https://oss.sonatype.org/content/repositories/releases" }
}
```
And then just list `chatty-core` as your dependency:
```groovy
dependencies {
    compile "lt.saltyjuice.dragas:chatty-core:1.3.1"
}
```
