## Unreleased

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
- Deprecated `heartbeatJob` in `DiscordEndpoint`.
- Deprecated `sequenceNumber` in `DiscordEndpoint`.
- Deprecated several callbacks, that are added via deprecated `addMessageHandler` method in `WebSocketEndpoint`