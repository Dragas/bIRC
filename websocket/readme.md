# Chatty/WebSocket

Chatty/Core implementation for websocket based connections. Much like Chatty/IRC, Chatty/WebSocket implements an 
`Input`/`Output` scheme, which helps the client implementations to get both requests and provide responses to the websocket.

Implementations should implement 4 classes, much like in Chatty/Core:
* WebSocketClient
* WebSocketAdapter
* WebSocketEndpoint
* WebSocketRoute 