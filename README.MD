# Chatty 

#### master ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=master)  websocket-dev ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=websocket-dev) discord-dev ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=discord-dev) biscord-dev ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=biscord-dev) irc-dev ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=irc-dev) birc-dev ![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=birc-dev) 

Somewhat elegant Java/Kotlin chat bot framework.

Includes both sources and implementation for IRCs, WebSockets and others!

## Adding Blackjack and Hookers

By the power of Declarative programming and reflection magic, you may use these the following annotations to enhance your chat bot experience!

- `@UsesControllers` - Marks that particular `Client` uses these `Controller` classes during runtime to 
check requests and try to respond to them. Note: that they need to extend `core.route.Controller`
- `@Before` - Marks either `Controller`, a `@On` method or `Client` that they use `BeforeMiddleware` to test
the request before using it. Note: Selected classes MUST implement `BeforeMiddleware` interface. There are 3 cases for this:
  - When `Client` is marked with this annotation, it tests the requests before pushing them to `Router` pipeline.
  - When `Controller` is marked with this annotation, it adds all mentioned middlewares to routes generated from it.
  - When `@On` method is marked with this annotation, it adds mentioned middlewares to route generated from it.
  - Note: You may only add particular middleware once to that route. So don't mention them twice in `@On` or `Controller`
- `@After` - Works the same as `@BeforeRequest`, BUT tests generated responses and uses `AfterMiddleware`.
- `@On` - marks a method in `Controller` that it can be used to generate responses. That method 
should return either `null` or `Response` object that was specified by implementing `Controller`. Requires `@When` annotation and a `clazz`
reference, which needs to match in `@When` call and this callback..
- `@When` - marks that a particular `@On` is tested with another method before it is used. That method
needs to have a single parameter `Request` and return a `Boolean` value.
- `@Description` - helps with router debugging. 

### How does it look?

```kotlin
class MockController() : Controller<MockRequest, MockResponse>()
{
    @On(MockRequest::class)
    @When("mockTest")
    @Before(MockBeforeMiddleware::class)
    @After(MockAfterMiddleware::class)
    @Description("A mock method brought to you by readme.md")
    fun mockResponseGenerator(mockRequest: MockRequest): MockResponse
    {
        return MockResponse(mockRequest.fieldValue, mockRequest.fieldName)
    }

    fun mockTest(mockRequest: MockRequest): Boolean
    {
        return true
    }
}
```

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
    compile "lt.saltyjuice.dragas:chatty-core:2.1.0"
}
```
