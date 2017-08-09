## [Unreleased]

## [0.2] 2017-08-09

### Added 

- `WebSocketInput` and `WebSocketOutput` wrappers for their core equivalents.
- `WebSocketRouter` implementation of `chatty.v3.core.route.Router`

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