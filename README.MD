# Chatty 

![Build Status](https://travis-ci.org/Dragas/bIRC.svg?branch=master)  

Elegant Java/Kotlin chat bot framework.

### How does it look?

```kotlin
@Singleton // marks that the controller should not be recreated after every request
@Before(AnotherMockBeforeMiddleware::class) // marks that any callbacks generated from this controller have this middleware
@After(AnotherMockAfterMiddleware::class) // read above.
class MockController() : Controller<MockRequest, MockResponse>()
{
    @On(MockRequest::class) // marks the method as callback and tests if request is or has a super class of the argument
    @When("mockTest") // marks which method is used to test the request
    // Note that you can't add same middleware twice to a route.
    @Before(MockBeforeMiddleware::class) // notes which middlewares test the request before actual testing
    @After(MockAfterMiddleware::class) // notes which middlewares test the generate responses
    @Description("A mock method brought to you by readme.md") // a description [shrug]
    fun mockResponseGenerator(mockRequest: MockRequest)
    {
        writeResponse(MockResponse(mockRequest.fieldValue, mockRequest.fieldName))
    }

    fun mockTest(mockRequest: MockRequest): Boolean
    {
        return true
    }
}
```

And then in your client
```kotlin
@UsesControllers(MockController::class) // Marks which controllers are used by the client
@Before(GlobalBeforeMiddleware::class) // adds this middleware to all routes generated in this client
@After(GlobalAfterMiddleware::class) // read above
class MockClient() : Client<String, Request, Response, String>
```

## Usage

Considering that `chatty-core` is just an abstraction set, you might want to look into either `chatty-irc`, `chatty-websocket`
or even their implementations: `chatty-birc` and `chatty-discord`(Websocket implementation for discord API) and 
`chatty-biscord`.

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
And then just list `chatty-core`/`chatty-websocket`/`chatty-irc` as your dependency:
```groovy
dependencies {
    compile "lt.saltyjuice.dragas:chatty-core:3.0.0"
}
```

Note: `chatty-irc` is still at `1.3.2`
