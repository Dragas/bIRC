## Unreleased

## [0.1.2]

### Changes

- Now `chatty-discord` depends on `chatty-websocket:2.2.0`

### Added

- Added `RateLimitInterceptor`. It's supposed to handle request rate limits for applications implenting this
framework. You may set `RateLimitInterceptor.shouldWait` to true, if instead of throwing, you want to wait for
request to go through instead. 
- Added remaining API calls for channel resource.


## [0.1.1] 2017-08-18

### Changes

- Instead of depending on raw project, `chatty-discord` now depends on `chatty-websocket:1.0.1`

## [0.1] 2017-08-17

### Added

- A lot of events that might come through the session handler.
- Added `DiscordRouter`, which is specifically meant for discord implementations.
- Added `DiscordMiddleware`, which is specifically meant for discord implementations
- Added `DiscordRoute` which is specifically meant for discord implementations
- Added `DiscordRouteBuilder` which is specifically meant for discord implementations.
- Added compressed message handler, which indirectly depends on `DiscordAdapter`.
- Added connection controller, which handles requests from websocket.
- Added Retrofit as a dependency to make it easier to manage HTTP endpoints for Discord API.
- Added OKHTTP as a side dependency for Retrofit to make it easier to handle requests.

### Changes

- `WebSocketEndpoint` is now in `chatty.v3.discord.main` package

### Removed

- Removed dependencies on deprecated APIs