#bIRC
Chatty/IRC implemented as an IRC bot.

##Running
Since the whole thing runs off java, all you need to do is run its binaries in your favorite
command line client. You should also have `settings.json` in same directory in order to customize
its default parameters, such as nickname, default channels, etc.

```json
{
  "address": "irc.server.com",
  "port": 6667,
  "user": "bIRC",
  "mode": "0",
  "unused": "*",
  "realname": "Chatty/IRC implementation",
  "nicknames": ["nicknames"],
  "channels": ["#channels"]
}
```