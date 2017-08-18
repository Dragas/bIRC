## [1.0.0] 2017-08-17

This marks Chatty/WebSocket release and it means that it can be downloaded from sonatype repositories.

### Removed

- Removed Deprecated APIs

## [Unreleased]

## [0.6] 2017-08-17


### Added

- `WebSocketRouterBuilder#type` to handle request type testing.

### Changes

- `WebSocketEndpoint#addMessageHandler` is now deprecated. Same functionality is handled by router.

## [0.5] 2017-08-16

### Added 
- Added methods that handle adding middleware to endpoint.

### Changes

- Changed how messages are handled in WebSocketEndpoint. Now `onMessage(Request)` is called when there is no
explicit handler for that message type.

## [0.4] 2017-08-14

### Added

- Added a workaround for `WebSocketEndpoint` which lets you add multiple message handlers.
- Added a `WebSocketCallback` which handles how multiple message handlers work.
- Added `readme.md`
- Added some more comments.
 
### Changes

- Reverted the change where you could try to declare multiple decoders.
- Undid the [0.3] change where `InputBlock` and `OutputBlock` parameters were muted.

### Removed

- Removed the redundant `ServerDestroyedException`

## [0.3] 2017-08-11

### Changes

- Entirely rewrote the WebSocket driver for Chatty/Core
- `WebSocketInput` and `WebSocketOutput` now mute `InputBlock` and `OutputBlock` parameters,
since that is handled by encoders.
- `WebSocketEndpoint.getAdapter()` now throws `NotImplementedException`.
- `WebSocketEndpoint` now opens and closes channels for requests and responses upon opening and closing
the session.
- `WebSocketClient` now requires a `WebSocketRouter`
- `WebSocketClient` now has internal websocket client, provided by Tyrus.
- `WebSocketRouter.builder()` now returns `WebSocketRouteBuilder` instead of regular `RouteBuilder`

### Removed

- Removed `WebSocketAdapter.deserialize(string, session)`.
- Removed `WebSocketEndpoint.onServerDestroyed()` method.
- Removed Base classes for requests and responses. Implementations of this driver should handle that.


## [0.2] 2017-08-09

### Added 

- `WebSocketInput` and `WebSocketOutput` wrappers for their core equivalents.
- `WebSocketRouter` implementation of `chatty.v3.core.route.Router`
- `WebSocketRouteBuilder` implementation of `chatty.v3.core.route.RouteBuilder` which returns `WebSocketRoute`

### Changed

- Chatty/WebSocket now depends on Chatty/Core 1.3.2
- Changed package declaration from `chatty` to `chatty.v3`
- Changed package declaration for `WebSocketClient` and `WebSocketEndpoint` from `chatty.websocket` to `chatty.v3.websocket.main` 

## [0.1] 2017-08-08

### Added

- Added barebones `WebSocketClient` implementation. Currently it's blocked due to #10
- Added `WebSocketAdapter` implementation.
- Added `WebSocketEndpoint` implementation.
- Added base classes for WebSocket requests and responses.