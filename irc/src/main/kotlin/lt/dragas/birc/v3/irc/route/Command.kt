package lt.dragas.birc.v3.irc.route

/**
 * Holds references to particular commands that Routes can implement and listen to.
 *
 * Definitions of particular commands (those not defined by Chatty/IRC in particular)
 * are shamelessly copied from [RFC 2812](https://tools.ietf.org/html/rfc2812)
 *
 * Even though this holds references to both actual commands and response codes, the response codes
 * are still treated as commands by framework.
 */
enum class Command(val value: String)
{
    /**
     * Instructs the router that it should return [PrivateMessageRoute] instead of usual [CommandRoute].
     *
     * Even though you can send a private message to both channel and user, there's a logical
     * difference between sending a private message to user and private message to channel,
     * hence the separation here as well.
     */
    PRIVATE_MESSAGE("private"),

    /**
     * Instructs a router that it should return [ChannelMessageRoute] instead of usual [CommandRoute].
     *
     * @see [PRIVATE_MESSAGE]
     */
    CHANNEL_MESSAGE("channel"),

    /* ---- Chatty/IRC specific definitions end ---- */

    /**
     * Command: PRIVMSG
     *
     * Parameters: `<msgtarget> <text to be sent>`
     *
     * PRIVMSG is used to send private messages between users, as well as to
     * send messages to channels. `<msgtarget>` is usually the nickname of
     * the recipient of the message, or a channel name.
     *
     * The `<msgtarget>` parameter may also be a host mask `(#<mask>)` or server
     * mask `($<mask>)`. In both cases the server will only send the PRIVMSG
     * to those who have a server or host matching the mask. The mask MUST
     * have at least 1 (one) "." in it and no wildcards following the last
     * ".". This requirement exists to prevent people sending messages to
     * "#*" or "$*", which would broadcast to all users. Wildcards are the
     * '*' and '?' characters. This extension to the PRIVMSG command is
     * only available to operators.
     *
     * Numeric Replies:
     *
     * * [ERR_NORECIPIENT]
     * * [ERR_NOTEXTTOSEND]
     * * [ERR_CANNOTSENDTOCHAN]
     * * [ERR_NOTOPLEVEL]
     * * [ERR_WILDTOPLEVEL]
     * * [ERR_TOOMANYTARGETS]
     * * [ERR_NOSUCHNICK]
     * * [RPL_AWAY]
     *
     * Examples:
     *
     * * :Angel!wings@irc.org PRIVMSG Wiz :Are you receiving this message ? ; Message from Angel to Wiz.
     * * PRIVMSG Angel :yes I'm receiving it !; Command to send a message to Angel.
     * * PRIVMSG jto@tolsun.oulu.fi :Hello ! ; Command to send a message to a user
     * on server tolsun.oulu.fi with username of "jto".
     * * PRIVMSG kalt%millennium.stealth.net@irc.stealth.net :Are you a frog? ; Message to a user on server
     * irc.stealth.net with username of "kalt", and connected from the host millennium.stealth.net.
     * * PRIVMSG kalt%millennium.stealth.net :Do you like cheese? ; Message to a user on the local
     * server with username of "kalt", and connected from the host millennium.stealth.net.
     * * PRIVMSG Wiz!jto@tolsun.oulu.fi :Hello ! ; Message to the user with nickname
     * Wiz who is connected from the host tolsun.oulu.fi and has the username "jto".
     * * PRIVMSG $*.fi :Server tolsun.oulu.fi rebooting. ; Message to everyone on a server
     * which has a name matching*.fi.
     * * PRIVMSG #*.edu :NSFNet is undergoing work, expect interruptions ; Message to all users who come from
     * a host which has a name matching *.edu.
     */
    PRIVMSG("PRIVMSG"),

    /**
     * Command: PING
     * Parameters: `<server1> [ <server2> ]`
     *
     * The PING command is used to test the presence of an active client or
     * server at the other end of the connection. Servers send a PING
     * message at regular intervals if no other activity detected coming
     * from a connection. If a connection fails to respond to a PING
     * message within a set amount of time, that connection is closed. A
     * PING message MAY be sent even if the connection is active.
     *
     * When a PING message is received, the appropriate [PONG] message MUST be
     * sent as reply to `<server1>` (server which sent the PING message out)
     * as soon as possible. If the `<server2>` parameter is specified, it
     * represents the target of the ping, and the message gets forwarded
     * there.
     *
     * Numeric Replies:
     *
     * * [ERR_NOORIGIN]
     * * [ERR_NOSUCHSERVER]
     *
     * Examples:
     *
     * * PING tolsun.oulu.fi ; Command to send a PING message to server
     * * PING WiZ tolsun.oulu.fi  ; Command from WiZ to send a PING message to server "tolsun.oulu.fi"
     * * PING :irc.funet.fi ; Ping message sent by server "irc.funet.fi"
     */
    PING("PING"),

    /**
     * Command: NICK
     *
     * Parameters: `<nickname>`
     *
     * NICK command is used to give user a nickname or change the existing
     * one.
     *
     * Numeric Replies:
     *
     * * [ERR_NONICKNAMEGIVEN]
     * * [ERR_ERRONEUSNICKNAME]
     * * [ERR_NICKNAMEINUSE]
     * * [ERR_NICKCOLLISION]
     * * [ERR_UNAVAILRESOURCE]
     * * [ERR_RESTRICTED]
     *
     * Examples:
     *
     * * NICK Wiz  ; Introducing new nick "Wiz" if session is
     * still unregistered, or user changing his
     * nickname to "Wiz"

     * * :WiZ!jto@tolsun.oulu.fi NICK Kilroy ; Server telling that WiZ changed his
     * nickname to Kilroy.
     */
    NICK("NICK"),

    /**
     * Command: PASS
     *
     * Parameters: `<password>`
     *
     * The PASS command is used to set a 'connection password'.  The
     * optional password can and MUST be set before any attempt to register
     * the connection is made.  Currently this requires that user send a
     * PASS command before sending the NICK/USER combination.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_ALREADYREGISTRED]
     *
     * Example:
     *
     * * PASS secretpasswordhere
     */
    PASS("PASS"),

    /**
     * Command: USER
     *
     * Parameters: `<user> <mode> <unused> <realname>`
     *
     * The USER command is used at the beginning of connection to specify
     * the username, hostname and realname of a new user.
     *
     * The <mode> parameter should be a numeric, and can be used to
     * automatically set user modes when registering with the server.  This
     * parameter is a bitmask, with only 2 bits having any signification: if
     * the bit 2 is set, the user mode 'w' will be set and if the bit 3 is
     * set, the user mode 'i' will be set.
     *
     * The `<realname>` may contain space characters, therefore should be abbreviated with ":".
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_ALREADYREGISTRED]
     *
     * Example:
     *
     * * USER guest 0 * :Ronnie Reagan ; User registering themselves with a
     * username of "guest" and real name "Ronnie Reagan".
     *
     * * USER guest 8 * :Ronnie Reagan ; User registering themselves with a
     * username of "guest" and real name "Ronnie Reagan", and asking to be set invisible.
     *
     * @see Command.MODE
     */
    USER("USER"),

    /**
     * Command: OPER
     *
     * Parameters: <name> <password>
     *
     * A normal user uses the OPER command to obtain operator privileges.
     * The combination of <name> and <password> are REQUIRED to gain
     * Operator privileges.  Upon success, the user will receive a MODE
     * message indicating the new user modes.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [RPL_YOUREOPER]
     * * [ERR_NOOPERHOST]
     * * [ERR_PASSWDMISMATCH]
     *
     * Example:
     *
     * OPER foo bar ; Attempt to register as an operator
     * using a username of "foo" and "bar"
     * as the password.
     *
     * @see Command.MODE
     */
    OPER("OPER"),


    /**
     * Since there are two definitions for MODE command, both of them will be listed here
     *
     * Command: MODE
     *
     * Parameters: `<nickname>*( ( "+" / "-" )*( "i" / "w" / "o" / "O" / "r" ) )`
     *
     * The user MODE's are typically changes which affect either how the
     * client is seen by others or what 'extra' messages the client is sent.
     *
     * A user MODE command MUST only be accepted if both the sender of the
     * message and the nickname given as a parameter are both the same.  If
     * no other parameter is given, then the server will return the current
     * settings for the nick.
     *
     * The available modes are as follows:
     *
     * * a - user is flagged as away;
     * * i - marks a users as invisible;
     * * w - user receives wallops;
     * * r - restricted user connection;
     * * o - operator flag;
     * * O - local operator flag;
     * * s - marks a user for receipt of server notices.
     *
     * Additional modes may be available later on.
     * The flag 'a' SHALL NOT be toggled by the user using the MODE command,
     * instead use of the AWAY command is REQUIRED.
     *
     * If a user attempts to make themselves an operator using the "+o" or
     * "+O" flag, the attempt SHOULD be ignored as users could bypass the
     * authentication mechanisms of the OPER command.  There is no
     * restriction, however, on anyone `deopping' themselves (using "-o" or
     * "-O").
     *
     * On the other hand, if a user attempts to make themselves unrestricted
     * using the "-r" flag, the attempt SHOULD be ignored.  There is no
     * restriction, however, on anyone `deopping' themselves (using "+r").
     * This flag is typically set by the server upon connection for
     * administrative reasons.  While the restrictions imposed are left up
     * to the implementation, it is typical that a restricted user not be
     * allowed to change nicknames, nor make use of the channel operator
     * status on channels.
     *
     * The flag 's' is obsolete but MAY still be used.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_USERSDONTMATCH]
     * * [ERR_UMODEUNKNOWNFLAG]
     * * [RPL_UMODEIS]
     *
     * Examples:
     * * MODE WiZ -w ; Command by WiZ to turn off reception of WALLOPS messages.
     * * MODE Angel +i ; Command from Angel to make herself invisible.
     * * MODE WiZ -o ; WiZ 'deopping' (removing operator status).
     *
     * Command: MODE
     *
     * Parameters: `<channel>*( ( "-" / "+" )*<modes>*<modeparams> )`
     *
     * The MODE command is provided so that users may query and change the
     * characteristics of a channel. For more details on available modes
     * and their uses, see "Internet Relay Chat: Channel Management" [IRC-
     * CHAN]. Note that there is a maximum limit of three (3) changes per
     * command for modes that take a parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_KEYSET]
     * * [ERR_NOCHANMODES]
     * * [ERR_CHANOPRIVSNEEDED]
     * * [ERR_USERNOTINCHANNEL]
     * * [ERR_UNKNOWNMODE]
     * * [RPL_CHANNELMODEIS]
     * * [RPL_BANLIST]
     * * [RPL_ENDOFBANLIST]
     * * [RPL_EXCEPTLIST]
     * * [RPL_ENDOFEXCEPTLIST]
     * * [RPL_INVITELIST]
     * * [RPL_ENDOFINVITELIST]
     * * [RPL_UNIQOPIS]
     *
     * The following examples are given to help understanding the syntax of
     * the MODE command, but refer to modes defined in "Internet Relay Chat:
     * Channel Management" [IRC-CHAN].
     *
     * Examples:
     *
     * * MODE #Finnish +imI*!*@*.fi  ; Command to make #Finnish channel moderated and
     * 'invite-only' with user with a hostname matching*.fi automatically invited.
     * * MODE #Finnish +o Kilroy  ; Command to give 'chanop' privileges to Kilroy on channel #Finnish.
     * * MODE #Finnish +v Wiz ; Command to allow WiZ to speak on #Finnish.
     * * MODE #Fins -s ; Command to remove 'secret' flag from channel #Fins.
     * * MODE #42 +k oulu ; Command to set the channel key to "oulu".
     * * MODE #42 -k oulu ; Command to remove the "oulu" channel key on channel "#42".
     * * MODE #eu-opers +l 10 ; Command to set the limit for the number of users on channel
     * "#eu-opers" to 10.
     * * :WiZ!jto@tolsun.oulu.fi MODE #eu-opers -l ; User "WiZ" removing the limit for
     * the number of users on channel "#eu-opers".
     * * MODE &oulu +b ; Command to list ban masks set for the channel "&oulu".
     * * MODE &oulu +b*!*@* ; Command to prevent all users from joining.
     * * MODE &oulu +b*!*@*.edu +e*!*@*.bu.edu ; Command to prevent any user from a
     * hostname matching*.edu from joining, except if matching*.bu.edu
     * * MODE #bu +be*!*@*.edu*!*@*.bu.edu ; Comment to prevent any user from a
     * hostname matching*.edu from joining, except if matching*.bu.edu
     * * MODE #meditation e ; Command to list exception masks set for the channel "#meditation".
     * * MODE #meditation I ; Command to list invitations masks set for the channel "#meditation".
     * * MODE !12345ircd O ; Command to ask who the channel creator for "!12345ircd" is.
     */
    MODE("MODE"),

    /**
     * Command: SERVICE
     *
     * Parameters: `<nickname> <reserved> <distribution> <type> <reserved> <info>`
     *
     * The SERVICE command to register a new service.  Command parameters
     * specify the service nickname, distribution, type and info of a new
     * service.
     * The `<distribution>` parameter is used to specify the visibility of a
     * service.  The service may only be known to servers which have a name
     * matching the distribution.  For a matching server to have knowledge
     * of the service, the network path between that server and the server
     * on which the service is connected MUST be composed of servers which
     * names all match the mask.
     *
     * The `<type>` parameter is currently reserved for future usage.
     *
     * Numeric Replies:
     *
     * * [ERR_ALREADYREGISTRED]
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_ERRONEUSNICKNAME]
     * * [RPL_YOURESERVICE]
     * * [RPL_YOURHOST]
     * * [RPL_MYINFO]
     *
     * Example:
     *
     * * SERVICE dict `* *`.fr 0 0 * :French Dictionary ; Service registering itself with a name of "dict".  This
     * service will only be available on servers which name matches "*.fr".
     */

    SERVICE("SERVICE"),

    /**
     * Command: QUIT
     *
     * Parameters: `[ <Quit Message> ]`
     *
     * A client session is terminated with a quit message.  The server
     * acknowledges this by sending an [ERROR] message to the client.
     *
     * Numeric Replies:
     *
     * * None.
     *
     * Example:
     *
     * * QUIT :Gone to have lunch ; Preferred message format.
     * * :syrk!kalt@millennium.stealth.net QUIT :Gone to have lunch ; User syrk has quit IRC to have lunch.
     */
    QUIT("QUIT"),

    /**
     * Command: SQUIT
     *
     * Parameters: `<server> <comment>`
     *
     * The SQUIT command is available only to operators.  It is used to
     * disconnect server links.  Also servers can generate SQUIT messages on
     * error conditions.  A SQUIT message may also target a remote server
     * connection.  In this case, the SQUIT message will simply be sent to
     * the remote server without affecting the servers in between the
     * operator and the remote server.
     *
     * The `<comment>` SHOULD be supplied by all operators who execute a SQUIT
     * for a remote server.  The server ordered to disconnect its peer
     * generates a WALLOPS message with `<comment>` included, so that other
     * users may be aware of the reason of this action.
     *
     * Numeric replies:
     *
     * * [ERR_NOPRIVILEGES]
     * * [ERR_NOSUCHSERVER]
     * * [ERR_NEEDMOREPARAMS]
     *
     * Examples:
     *
     * * SQUIT tolsun.oulu.fi :Bad Link ?  ; Command to uplink of the server tolson.oulu.fi to terminate its connection with comment "Bad Link".
     * * :Trillian SQUIT cm22.eng.umd.edu :Server out of control ; Command from Trillian from to disconnect "cm22.eng.umd.edu" from the net with
     * comment "Server out of control".
     */
    SQUIT("SQUIT"),

    /**
     * Command: JOIN
     *
     * Parameters: `( <channel>*( "," <channel> ) [ <key>*( "," <key> ) ] ) / "0"`
     *
     * The JOIN command is used by a user to request to start listening to
     * the specific channel.  Servers MUST be able to parse arguments in the
     * form of a list of target, but SHOULD NOT use lists when sending JOIN
     * messages to clients.
     *
     * Once a user has joined a channel, he receives information about
     * all commands his server receives affecting the channel.  This
     * includes [JOIN], [MODE], [KICK], [PART], [QUIT] and of course [PRIVMSG]/[NOTICE].
     * This allows channel members to keep track of the other channel
     * members, as well as channel modes.
     *
     * If a JOIN is successful, the user receives a JOIN message as
     * confirmation and is then sent the channel's topic (using [RPL_TOPIC]) and
     * the list of users who are on the channel (using [RPL_NAMEREPLY]), which
     * MUST include the user joining.
     *
     * Note that this message accepts a special argument ("0"), which is
     * a special request to leave all channels the user is currently a member
     * of. The server will process this message as if the user had sent
     * a [PART] command for each channel he is a member of.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_BANNEDFROMCHAN]
     * * [ERR_INVITEONLYCHAN]
     * * [ERR_BADCHANNELKEY]
     * * [ERR_CHANNELISFULL]
     * * [ERR_BADCHANMASK]
     * * [ERR_NOSUCHCHANNEL]
     * * [ERR_TOOMANYCHANNELS]
     * * [ERR_TOOMANYTARGETS]
     * * [ERR_UNAVAILRESOURCE]
     * * [RPL_TOPIC]
     *
     * Examples:
     *
     * * JOIN #foobar ; Command to join channel #foobar.
     * * JOIN &foo fubar ; Command to join channel &foo using key "fubar".
     * * JOIN #foo,&bar fubar ; Command to join channel #foo using key "fubar" and &bar using no key.
     * * JOIN #foo,#bar fubar,foobar   ; Command to join channel #foo using
     * key "fubar", and channel #bar using key "foobar".
     * * JOIN #foo,#bar ; Command to join channels #foo and #bar.
     * * JOIN 0 ; Leave all currently joined channels.
     * * :WiZ!jto@tolsun.oulu.fi JOIN #Twilight_zone ; JOIN message from WiZ on channel #Twilight_zone
     */
    JOIN("JOIN"),

    /**
     * Command: PART
     *
     * Parameters: `<channel>*( "," <channel> ) [ <Part Message> ]`
     *
     * The PART command causes the user sending the message to be removed
     * from the list of active members for all given channels listed in the
     * parameter string.  If a "Part Message" is given, this will be sent
     * instead of the default message, the nickname.  This request is always
     * granted by the server.
     *
     * Servers MUST be able to parse arguments in the form of a list of
     * target, but SHOULD NOT use lists when sending PART messages to
     * clients.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_NOSUCHCHANNEL]
     * * [ERR_NOTONCHANNEL]
     *
     * Examples:
     *
     * * PART #twilight_zone ; Command to leave channel "#twilight_zone"
     * * PART #oz-ops,&group5 ; Command to leave both channels "&group5" and "#oz-ops".
     * * :WiZ!jto@tolsun.oulu.fi PART #playzone :I lost ; User WiZ leaving channel "#playzone" with the message "I lost".
     */
    PART("PART"),

    /**
     * Command: TOPIC
     *
     * Parameters: `<channel> [ <topic> ]`
     *
     * The TOPIC command is used to change or view the topic of a channel.
     * The topic for channel `<channel>` is returned if there is no `<topic>`
     * given. If the `<topic>` parameter is present, the topic for that
     * channel will be changed, if this action is allowed for the user
     * requesting it. If the `<topic>` parameter is an empty string, the
     * topic for that channel will be removed.

     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_NOTONCHANNEL]
     * * [RPL_NOTOPIC]
     * * [RPL_TOPIC]
     * * [ERR_CHANOPRIVSNEEDED]
     * * [ERR_NOCHANMODES]
     *
     * Examples:
     *
     * * :WiZ!jto@tolsun.oulu.fi TOPIC #test :New topic ; User Wiz setting the* topic.
     * * TOPIC #test :another topic ; Command to set the topic on #test
     * to "another topic".
     * * TOPIC #test : ; Command to clear the topic on #test.
     * * TOPIC #test ; Command to check the topic for #test.
     */
    TOPIC("TOPIC"),

    /**
     * Command: NAMES
     *
     * Parameters: `[ <channel>*( "," <channel> ) [ <target> ] ]`
     *
     * By using the NAMES command, a user can list all nicknames that are
     * visible to him. For more details on what is visible and what is not,
     * see "Internet Relay Chat: Channel Management" [IRC-CHAN]. The
     * `<channel>` parameter specifies which channel(s) to return information
     * about. There is no error reply for bad channel names.
     *
     * If no `<channel>` parameter is given, a list of all channels and their
     * occupants is returned. At the end of this list, a list of users who
     * are visible but either not on any channel or not on a visible channel
     * are listed as being on `channel' "*".
     *
     * If the `<target>` parameter is specified, the request is forwarded to
     * that server which will generate the
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numerics:
     *
     * * [ERR_TOOMANYMATCHES]
     * * [ERR_NOSUCHSERVER]
     * * [RPL_NAMEREPLY]
     * * [RPL_ENDOFNAMES]
     *
     * Examples:
     *
     * * NAMES #twilight_zone,#42  ; Command to list visible users on #twilight_zone and #42
     * * NAMES ; Command to list all visible channels and users
     */
    NAMES("NAMES"),

    /**
     * Command: LIST
     *
     * Parameters: `[ <channel>*( "," <channel> ) [ <target> ] ]`
     *
     * The list command is used to list channels and their topics. If the
     * `<channel>` parameter is used, only the status of that channel is
     * displayed.
     *
     * If the `<target>` parameter is specified, the request is forwarded to
     * that server which will generate the
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_TOOMANYMATCHES]
     * * [ERR_NOSUCHSERVER]
     * * [RPL_LIST]
     * * [RPL_LISTEND]
     *
     * Examples:
     *
     * * LIST ; Command to list all channels.
     * * LIST #twilight_zone,#42  ; Command to list channels #twilight_zone and #42
     */
    LIST("LIST"),

    /**
     * Command: INVITE
     *
     * Parameters: `<nickname> <channel>`
     *
     * The INVITE command is used to invite a user to a channel. The
     * parameter `<nickname>` is the nickname of the person to be invited to
     * the target channel `<channel>`. There is no requirement that the
     * channel the target user is being invited to must exist or be a valid
     * channel. However, if the channel exists, only members of the channel
     * are allowed to invite other users. When the channel has invite-only
     * flag set, only channel operators may issue INVITE command.
     *
     * Only the user inviting and the user being invited will receive
     * notification of the invitation. Other channel members are not
     * notified. (This is unlike the [MODE] changes, and is occasionally the
     * source of trouble for users.)
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_NOSUCHNICK]
     * * [ERR_NOTONCHANNEL]
     * * [ERR_USERONCHANNEL]
     * * [ERR_CHANOPRIVSNEEDED]
     * * [RPL_INVITING]
     * * [RPL_AWAY]
     *
     * Examples:
     *
     * * :Angel!wings@irc.org INVITE Wiz #Dust ; Message to WiZ when he has been
     * invited by user Angel to channel #Dust
     * * INVITE Wiz #Twilight_Zone  ; Command to invite WiZ to
     * #Twilight_zone
     */
    INVITE("INVITE"),

    /**
     * Command: KICK
     *
     * Parameters: `<channel> *( "," <channel> ) <user> *( "," <user> ) [<comment>]`
     *
     * The KICK command can be used to request the forced remoof a user
     * from a channel. It causes the `<user>` to [PART] from the `<channel>` by
     * force. For the message to be syntactically correct, there MUST be
     * either one channel parameter and multiple user parameter, or as many
     * channel parameters as there are user parameters. If a "comment" is
     * given, this will be sent instead of the default message, the nickname
     * of the user issuing the [KICK].
     *
     * The server MUST NOT send [KICK] messages with multiple channels or
     * users to clients. This is necessarily to maintain backward
     * compatibility with old client software.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_NOSUCHCHANNEL]
     * * [ERR_BADCHANMASK]
     * * [ERR_CHANOPRIVSNEEDED]
     * * [ERR_USERNOTINCHANNEL]
     * * [ERR_NOTONCHANNEL]
     *
     * Examples:
     *
     * * KICK &Melbourne Matthew  ; Command to kick Matthew from &Melbourne
     * * KICK #Finnish John :Speaking English ; Command to kick John from #Finnish
     * using "Speaking English" as the reason (comment).
     * * :WiZ!jto@tolsun.oulu.fi KICK #Finnish John ; KICK message on channel #Finnish
     * from WiZ to remove John from channel
     */
    KICK("KICK"),

    /**
     * Command: NOTICE
     *
     * Parameters: `<msgtarget> <text>`
     *
     * The NOTICE command is used similarly to PRIVMSG. The difference
     * between NOTICE and PRIVMSG is that automatic replies MUST NEVER be
     * sent in response to a NOTICE message. This rule applies to servers
     * too - they MUST NOT send any error reply back to the client on
     * receipt of a notice. The object of this rule is to avoid loops
     * between clients automatically sending something in response to
     * something it received.
     *
     * This command is available to services as well as users.
     *
     * This is typically used by services, and automatons (clients with
     * either an AI or other interactive program controlling their actions).
     *
     * See PRIVMSG for more details on replies and examples.
     */
    NOTICE("NOTICE"),

    /**
     * Command: MOTD
     *
     * Parameters: `[ <target> ]`
     *
     * The MOTD command is used to get the "Message Of The Day" of the given
     * server, or current server if `<target>` is omitted.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     * * [RPL_MOTDSTART]
     * * [RPL_MOTD]
     * * [RPL_ENDOFMOTD]
     * * [ERR_NOMOTD]
     */
    MOTD("MOTD"),

    /**
     *
     * Command: LUSERS
     *
     * Parameters: `[ <mask> [ <target> ] ]`
     *
     * The LUSERS command is used to get statistics about the size of the
     * IRC network. If no parameter is given, the reply will be about the
     * whole net. If a `<mask>` is specified, then the reply will only
     * concern the part of the network formed by the servers matching the
     * mask. Finally, if the `<target>` parameter is specified, the request
     * is forwarded to that server which will generate the
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [RPL_LUSERCLIENT]
     * * [RPL_LUSEROP]
     * * [RPL_LUSERUNKNOWN]
     * * [RPL_LUSERCHANNELS]
     * * [RPL_LUSERME]
     * * [ERR_NOSUCHSERVER]
     */
    LUSER("LUSER"),

    /**
     * Command: VERSION
     *
     * Parameters: `[ <target> ]`
     *
     * The VERSION command is used to query the version of the server
     * program. An optional parameter `<target>` is used to query the version
     * of the server program which a client is not directly connected to.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_VERSION]
     *
     * Examples:
     *
     * VERSION tolsun.oulu.fi  ; Command to check the version of server "tolsun.oulu.fi".
     */
    VERSION("VERSION"),

    /**
     * Command: STATS
     * Parameters: `[ <query> [ <target> ] ]`
     *
     * The stats command is used to query statistics of certain server. If
     * `<query>` parameter is omitted, only the end of stats reply is sent
     * back.
     *
     * A query may be given for any single letter which is only checked by
     * the destination server and is otherwise passed on by intermediate
     * servers, ignored and unaltered.
     *
     * Wildcards are allowed in the `<target>` parameter.
     * Except for the ones below, the list of valid queries is
     * implementation dependent. The standard queries below SHOULD be
     * supported by the server:
     * * l - returns a list of the server's connections, showing how
     * long each connection has been established and the
     * traffic over that connection in Kbytes and messages for
     * each direction;
     * * m - returns the usage count for each of commands supported
     * by the server; commands for which the usage count is
     * zero MAY be omitted;
     * * o - returns a list of configured privileged users,
     * operators;
     * * u - returns a string showing how long the server has been
     * up.
     *
     * It is also RECOMMENDED that client and server access configuration be
     * published this way.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_STATSLINKINFO]
     * * [RPL_STATSUPTIME]
     * * [RPL_STATSCOMMANDS]
     * * [RPL_STATSOLINE]
     * * [RPL_ENDOFSTATS]
     *
     * Examples:
     *
     * STATS m ; Command to check the command usage for the server you are connected to
     */
    STATS("STATS"),

    /**
     * Command: LINKS
     *
     * Parameters: `[ [ <remote server> ] <server mask> ]`
     *
     * With LINKS, a user can list all servernames, which are known by the
     * server answering the query. The returned list of servers MUST match
     * the mask, or if no mask is given, the full list is returned.
     *
     * If `<remote server>` is given in addition to `<server mask>`, the LINKS
     * command is forwarded to the first server found that matches that name
     * (if any), and that server is then required to answer the query.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_LINKS]
     * * [RPL_ENDOFLINKS]
     *
     * Examples:
     *
     * * LINKS *.au ; Command to list all servers which have a name that matches*.au;
     * * LINKS *.edu *.bu.edu ; Command to list servers matching *.bu.edu as seen by the first server
     * matching *.edu.
     */
    LINKS("LINKS"),
    /**
     * Command: TIME
     * Parameters:` [ <target> ]`
     *
     * The time command is used to query local time from the specified
     * server. If the `<target>` parameter is not given, the server receiving
     * the command must reply to the query.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_TIME]
     *
     * Examples:
     *
     * * TIME tolsun.oulu.fi ; check the time on the serve "tolson.oulu.fi"
     */
    TIME("TIME"),
    /**
     * Command: CONNECT
     *
     * Parameters: `<target server> <port> [ <remote server> ]`
     *
     * The CONNECT command can be used to request a server to try to
     * establish a new connection to another server immediately. CONNECT is
     * a privileged command and SHOULD be available only to IRC Operators.
     * If a `<remote server>` is given and its mask doesn't match name of the
     * parsing server, the CONNECT attempt is sent to the first match of
     * remote server. Otherwise the CONNECT attempt is made by the server
     * processing the request.
     *
     * The server receiving a remote CONNECT command SHOULD generate a
     * WALLOPS message describing the source and target of the request.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [ERR_NOPRIVILEGES]
     * * [ERR_NEEDMOREPARAMS]
     *
     * Examples:
     *
     * CONNECT tolsun.oulu.fi 6667  ; Command to attempt to connect local server to
     * tolsun.oulu.fi on port 6667
     */
    CONNECT("CONNECT"),

    /**
     * Command: TRACE
     *
     * Parameters: `[ <target> ]`
     *
     * TRACE command is used to find the route to specific server and
     * information about its peers. Each server that processes this command
     * MUST report to the sender about it. The replies from pass-through
     * links form a chain, which shows route to destination. After sending
     * this reply back, the query MUST be sent to the next server until
     * given `<target>` server is reached.
     *
     * TRACE command is used to find the route to specific server. Each
     * server that processes this message MUST tell the sender about it by
     * sending a reply indicating it is a pass-through link, forming a chain
     * of replies. After sending this reply back, it MUST then send the
     * TRACE message to the next server until given server is reached. If
     * the `<target>` parameter is omitted, it is RECOMMENDED that TRACE
     * command sends a message to the sender telling which servers the local
     * server has direct connection to.
     *
     * If the destination given by `<target>` is an actual server, the
     * destination server is REQUIRED to report all servers, services and
     * operators which are connected to it; if the command was issued by an
     * operator, the server MAY also report all users which are connected to
     * it. If the destination given by `<target>` is a nickname, then only a
     * reply for that nickname is given. If the `<target>` parameter is
     * omitted, it is RECOMMENDED that the TRACE command is parsed as
     * targeted to the processing server.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * If the TRACE message is destined for another server, all
     * intermediate servers must return a [RPL_TRACELINK] reply to indicate
     * that the TRACE passed through it and where it is going next.
     * * [RPL_TRACELINK]
     *
     * A TRACE reply may be composed of any number of the following
     * numeric replies.
     *
     * * [RPL_TRACEHANDSHAKE]
     * * [RPL_TRACECONNECTING]
     * * [RPL_TRACEUNKNOWN]
     * * [RPL_TRACEOPERATOR]
     * * [RPL_TRACEUSER]
     * * [RPL_TRACESERVER]
     * * [RPL_TRACESERVICE]
     * * [RPL_TRACENEWTYPE]
     * * [RPL_TRACECLASS]
     * * [RPL_TRACELOG]
     * * [RPL_TRACEEND]
     *
     * Examples:
     * * TRACE *.oulu.fi ; TRACE to a server matching *.oulu.fi
     */
    TRACE("TRACE"),

    /**
     * Command: ADMIN
     *
     * Parameters: `[ <target> ]`
     *
     * The admin command is used to find information about the administrator
     * of the given server, or current server if `<target>` parameter is
     * omitted. Each server MUST have the ability to forward ADMIN messages
     * to other servers.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_ADMINME]
     * * [RPL_ADMINLOC1]
     * * [RPL_ADMINLOC2]
     * * [RPL_ADMINEMAIL]
     *
     * Examples:
     *
     * * ADMIN tolsun.oulu.fi ; request an ADMIN reply from tolsun.oulu.fi
     * * ADMIN syrk  ; ADMIN request for the server to
     * which the user syrk is connected
     */
    ADMIN("ADMIN"),

    /**
     * Command: INFO
     *
     * Parameters: `[ <target> ]`
     *
     * The INFO command is REQUIRED to return information describing the
     * server: its version, when it was compiled, the patchlevel, when it
     * was started, and any other miscellaneous information which may be
     * considered to be relevant.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_INFO]
     * * [RPL_ENDOFINFO]
     *
     * Examples:
     *
     * * INFO csd.bu.edu ; request an INFO reply from csd.bu.edu
     * * INFO Angel ; request info from the server that Angel is connected to.
     */
    INFO("INFO"),

    /**
     * Command: SERVLIST
     *
     * Parameters: `[ <mask> [ <type> ] ]`
     *
     * The SERVLIST command is used to list services currently connected to
     * the network and visible to the user issuing the command. The
     * optional parameters may be used to restrict the result of the query
     * (to matching services names, and services type).
     *
     * Numeric Replies:
     *
     * * [RPL_SERVLIST]
     * * [RPL_SERVLISTEND]
     */
    SERVLIST("SERVLIST"),


    /**
     * Command: SQUERY
     *
     * Parameters: `<servicename> <text>`
     *
     * The SQUERY command is used similarly to [PRIVMSG]. The only difference
     * is that the recipient MUST be a service. This is the only way for a
     * text message to be delivered to a service.
     *
     * Examples:
     *
     * * SQUERY irchelp :HELP privmsg ; Message to the service with nickname irchelp.
     * * SQUERY dict@irc.fr :fr2en blaireau ; Message to the service with name
     * * dict@irc.fr.
     *
     * @see PRIVMSG
     */
    SQUERY("SQUERY"),

    /**
     * Command: WHO
     *
     * Parameters: `[ <mask> [ "o" ] ]`
     *
     * The WHO command is used by a client to generate a query which returns
     * a list of information which 'matches' the `<mask>` parameter given by
     * the client. In the absence of the `<mask>` parameter, all visible
     * (users who aren't invisible (user mode +i) and who don't have a
     * common channel with the requesting client) are listed. The same
     * result can be achieved by using a `<mask>` of "0" or any wildcard which
     * will end up matching every visible user.
     *
     * The `<mask>` passed to WHO is matched against users' host, server, real
     * name and nickname if the channel `<mask>` cannot be found.
     *
     * If the "o" parameter is passed only operators are returned according
     * to the `<mask>` supplied.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [RPL_WHOREPLY]
     * * [RPL_ENDOFWHO]
     *
     * Examples:
     *
     * * WHO *.fi ; Command to list all users who match against "*.fi".
     * * WHO jto* o ; Command to list all users with a match against "jto*" if they are an
     * operator.
     */
    WHO("WHO"),

    /**
     * Command: WHOIS
     *
     * Parameters: `[ <target> ] <mask>*( "," <mask> )`
     *
     * This command is used to query information about particular user.
     * The server will answer this command with several numeric messages
     * indicating different statuses of each user which matches the mask (if
     * you are entitled to see them). If no wildcard is present in the
     * `<mask>`, any information about that nick which you are allowed to see
     * is presented.
     *
     * If the `<target>` parameter is specified, it sends the query to a
     * specific server. It is useful if you want to know how long the user
     * in question has been idle as only local server (i.e., the server the
     * user is directly connected to) knows that information, while
     * everything else is globally known.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [ERR_NONICKNAMEGIVEN]
     * * [RPL_WHOISUSER]
     * * [RPL_WHOISCHANNELS]
     * * [RPL_WHOISCHANNELS]
     * * [RPL_WHOISSERVER]
     * * [RPL_AWAY]
     * * [RPL_WHOISOPERATOR]
     * * [RPL_WHOISIDLE]
     * * [ERR_NOSUCHNICK]
     * * [RPL_ENDOFWHOIS]
     *
     * Examples:
     *
     * * WHOIS wiz ; return available user information about nick WiZ
     * * WHOIS eff.org trillian  ; ask server eff.org for user information about trillian
     */
    WHOIS("WHOIS"),

    /**
     * Command: WHOWAS
     *
     * Parameters: `<nickname> *( "," <nickname> ) [ <count> [ <target> ] ]`
     *
     * Whowas asks for information about a nickname which no longer exists.
     * This may either be due to a nickname change or the user leaving IRC.
     * In response to this query, the server searches through its nickname
     * history, looking for any nicks which are lexically the same (no wild
     * card matching here). The history is searched backward, returning the
     * most recent entry first. If there are multiple entries, up to
     * `<count>` replies will be returned (or all of them if no `<count>`
     * parameter is given). If a non-positive number is passed as being
     * `<count>`, then a full search is done.
     *
     * Wildcards are allowed in the `<target>` parameter.
     *
     * Numeric Replies:
     *
     * * [ERR_WASNOSUCHNICK]
     * * [ERR_NONICKNAMEGIVEN]
     * * [RPL_WHOWASUSER]
     * * [RPL_WHOISSERVER]
     * * [RPL_ENDOFWHOWAS]
     *
     * Examples:
     *
     * * WHOWAS Wiz  ; return all information in the nick history about nick "WiZ";
     * * WHOWAS Mermaid 9 ; return at most, the 9 most recent entries in the nick history for
     * "Mermaid";
     * * WHOWAS Trillian 1 *.edu  ; return the most recent history for "Trillian" from the first server
     * found to match "*.edu".
     */
    WHOWAS("WHOWAS"),

    /**
     * Command: KILL
     *
     * Parameters: `<nickname> <comment>`
     *
     * The KILL command is used to cause a client-server connection to be
     * closed by the server which has the actual connection. Servers
     * generate KILL messages on nickname collisions. It MAY also be
     * available available to users who have the operator status.
     *
     * Clients which have automatic reconnect algorithms effectively make
     * this command useless since the disconnection is only brief. It does
     * however break the flow of data and can be used to stop large amounts
     * of 'flooding' from abusive users or accidents. Abusive users usually
     * don't care as they will reconnect promptly and resume their abusive
     * behaviour. To prevent this command from being abused, any user may
     * elect to receive KILL messages generated for others to keep an 'eye'
     * on would be trouble spots.
     *
     * In an arena where nicknames are REQUIRED to be globally unique at all
     * times, KILL messages are sent whenever 'duplicates' are detected
     * (that is an attempt to register two users with the same nickname) in
     * the hope that both of them will disappear and only 1 reappear.
     *
     * When a client is removed as the result of a KILL message, the server
     * SHOULD add the nickname to the list of unavailable nicknames in an
     * attempt to avoid clients to reuse this name immediately which is
     * usually the pattern of abusive behaviour often leading to useless
     * "KILL loops". See the "IRC Server Protocol" document [IRC-SERVER]
     * for more information on this procedure.
     *
     * The comment given MUST reflect the actual reason for the KILL. For
     * server-generated KILLs it usually is made up of details concerning
     * the origins of the two conflicting nicknames. For users it is left
     * up to them to provide an adequate reason to satisfy others who see
     * it. To prevent/discourage fake KILLs from being generated to hide
     * the identify of the KILLer, the comment also shows a 'kill-path'
     * which is updated by each server it passes through, each prepending
     * its name to the path.
     *
     * Numeric Replies:
     *
     * * [ERR_NOPRIVILEGES]
     * * [ERR_NEEDMOREPARAMS]
     * * [ERR_NOSUCHNICK]
     * * [ERR_CANTKILLSERVER]
     *
     * NOTE:
     * It is RECOMMENDED that only Operators be allowed to kill other users
     * with KILL command. This command has been the subject of many
     * controversies over the years, and along with the above
     * recommendation, it is also widely recognized that not even operators
     * should be allowed to kill users on remote servers.
     */
    KILL("KILL"),

    /**
     * Command: PONG
     *
     * Parameters: `<server> [ <server2> ]`
     *
     * PONG message is a reply to ping message. If parameter <server2> is
     * given, this message MUST be forwarded to given target. The <server>
     * parameter is the name of the entity who has responded to PING message
     * and generated this message.
     *
     * Numeric Replies:
     *
     * * [ERR_NOORIGIN]
     * * [ERR_NOSUCHSERVER]
     *
     * Example:
     *
     * PONG csd.bu.edu tolsun.oulu.fi ; PONG message from csd.bu.edu to tolsun.oulu.fi
     */
    PONG("PONG"),

    /**
     * Command: ERROR
     *
     * Parameters: `<error message>`
     *
     * The ERROR command is for use by servers when reporting a serious or
     * fatal error to its peers. It may also be sent from one server to
     * another but MUST NOT be accepted from any normal unknown clients.
     *
     * Only an ERROR message SHOULD be used for reporting errors which occur
     * with a server-to-server link. An ERROR message is sent to the server
     * at the other end (which reports it to appropriate local users and
     * logs) and to appropriate local users and logs. It is not to be
     * passed onto any other servers by a server if it is received from a
     * server.
     *
     * The ERROR message is also used before terminating a client
     * connection.
     *
     * When a server sends a received ERROR message to its operators, the
     * message SHOULD be encapsulated inside a NOTICE message, indicating
     * that the client was not responsible for the error.
     *
     * Numeric replies:
     *
     * * None.
     *
     * Examples:
     *
     * * ERROR :Server *.fi already exists ; ERROR message to the other server which caused this error.
     * * NOTICE WiZ :ERROR from csd.bu.edu -- Server *.fi already exists ; Same ERROR message as above but
     * sent to user WiZ on the other server.
     */
    ERROR("ERROR"),

    /**
     * Command: AWAY
     *
     * Parameters: `[ <text> ]`
     *
     * With the AWAY command, clients can set an automatic reply string for
     * any [PRIVMSG] commands directed at them (not to a channel they are on).
     * The server sends an automatic reply to the client sending the [PRIVMSG]
     * command. The only replying server is the one to which the sending
     * client is connected to.
     *
     * The AWAY command is used either with one parameter, to set an AWAY
     * message, or with no parameters, to remove the AWAY message.
     *
     * Because of its high cost (memory and bandwidth wise), the AWAY
     * message SHOULD only be used for client-server communication. A
     * server MAY choose to silently ignore AWAY messages received from
     * other servers. To update the away status of a client across servers,
     * the user [MODE] 'a' SHOULD be used instead.
     *
     * Numeric Replies:
     *
     * * [RPL_UNAWAY]
     * * [RPL_NOWAWAY]
     *
     * Example:
     *
     * * AWAY :Gone to lunch. Back in 5 ; Command to set away message to "Gone to lunch. Back in 5".
     *
     * @see MODE
     */
    AWAY("AWAY"),

    /**
     * Command: REHASH
     *
     * Parameters: None
     *
     * The rehash command is an administrative command which can be used by
     * an operator to force the server to re-read and process its
     * configuration file.
     *
     * Numeric Replies:
     *
     * * [RPL_REHASHING]
     * * [ERR_NOPRIVILEGES]
     *
     * Example:
     *
     * * REHASH ; message from user with operator status to server asking it to reread
     * its configuration file.
     */
    REHASH("REHASH"),

    /**
     * Command: DIE
     *
     * Parameters: None
     *
     * An operator can use the DIE command to shutdown the server. This
     * message is optional since it may be viewed as a risk to allow
     * arbitrary people to connect to a server as an operator and execute
     * this command.
     *
     * The DIE command MUST always be fully processed by the server to which
     * the sending client is connected and MUST NOT be passed onto other
     * connected servers.
     *
     * Numeric Replies:
     *
     * * [ERR_NOPRIVILEGES]
     *
     * Example:
     *
     * * DIE ; no parameters required.
     */
    DIE("DIE"),

    /**
     * Command: RESTART
     *
     * Parameters: None
     *
     * An operator can use the restart command to force the server to
     * restart itself. This message is optional since it may be viewed as a
     * risk to allow arbitrary people to connect to a server as an operator
     * and execute this command, causing (at least) a disruption to service.
     *
     * The RESTART command MUST always be fully processed by the server to
     * which the sending client is connected and MUST NOT be passed onto
     * other connected servers.
     *
     * Numeric Replies:
     *
     * * [ERR_NOPRIVILEGES]
     *
     * Example:
     *
     * RESTART ; no parameters required.
     */
    RESTART("RESTART"),

    /**
     * Command: SUMMON
     *
     * Parameters: `<user> [ <target> [ <channel> ] ]`
     *
     * The SUMMON command can be used to give users who are on a host
     * running an IRC server a message asking them to please join IRC. This
     * message is only sent if the target server (a) has SUMMON enabled, (b)
     * the user is logged in and (c) the server process can write to the
     * user's tty (or similar).
     *
     * If no `<server>` parameter is given it tries to summon `<user>` from the
     * server the client is connected to is assumed as the target.
     *
     * If summon is not enabled in a server, it MUST return the
     * [ERR_SUMMONDISABLED] numeric.
     *
     * Numeric Replies:
     *
     * * [ERR_NORECIPIENT]
     * * [ERR_FILEERROR]
     * * [ERR_NOLOGIN]
     * * [ERR_NOSUCHSERVER]
     * * [ERR_SUMMONDISABLED]
     * * [RPL_SUMMONING]
     *
     * Examples:
     *
     * * SUMMON jto ; summon user jto on the server's host
     * * SUMMON jto tolsun.oulu.fi  ; summon user jto on the host which a server named "tolsun.oulu.fi" is
     * running.
     */
    SUMMON("SUMMON"),

    /**
     * Command: USERS
     *
     * Parameters: `[ <target> ]`
     *
     * The USERS command returns a list of users logged into the server in a
     * format similar to the UNIX commands who(1), rusers(1) and finger(1).
     * If disabled, the correct numeric MUST be returned to indicate this.
     *
     * Because of the security implications of such a command, it SHOULD be
     * disabled by default in server implementations. Enabling it SHOULD
     * require recompiling the server or some equivalent change rather than
     * simply toggling an option and restarting the server. The procedure
     * to enable this command SHOULD also include suitable large comments.
     *
     * Numeric Replies:
     *
     * * [ERR_NOSUCHSERVER]
     * * [ERR_FILEERROR]
     * * [RPL_USERSSTART]
     * * [RPL_USERS]
     * * [RPL_NOUSERS]
     * * [RPL_ENDOFUSERS]
     * * [ERR_USERSDISABLED]
     *
     * Disabled Reply:
     *
     * * [ERR_USERSDISABLED]
     *
     * Example:
     *
     * * USERS eff.org ; request a list of users logged in on server eff.org
     */
    USERS("USERS"),


    /**
     * Command: WALLOPS
     *
     * Parameters: `<Text to be sent>`
     *
     * The WALLOPS command is used to send a message to all currently
     * connected users who have set the 'w' user [MODE] for themselves.
     *
     * After implementing WALLOPS as a user command it was found that it was
     * often and commonly abused as a means of sending a message to a lot of
     * people. Due to this, it is RECOMMENDED that the implementation of
     * WALLOPS allows and recognizes only servers as the originators of
     * WALLOPS.
     *
     * Numeric Replies:
     *
     * * [ERR_NEEDMOREPARAMS]
     * * 481
     *
     * Example:
     *
     * * :csd.bu.edu WALLOPS :Connect '*.uiuc.edu 6667' from Joshua ; WALLOPS message from
     * csd.bu.edu announcing a CONNECT message it received from Joshua and acted upon.
     */
    WALLOPS("WALLOPS"),

    /**
     * Command: USERHOST
     *
     * Parameters: `<nickname> *( SPACE <nickname> )`
     *
     * The USERHOST command takes a list of up to 5 nicknames, each
     * separated by a space character and returns a list of information
     * about each nickname that it found. The returned list has each reply
     * separated by a space.
     *
     * Numeric Replies:
     *
     * * [RPL_USERHOST]
     * * [ERR_NEEDMOREPARAMS]
     *
     * Example:
     *
     * * USERHOST Wiz Michael syrk  ; USERHOST request for information on nicks "Wiz", "Michael", and "syrk"
     * * :ircd.stealth.net 302 yournick :syrk=+syrk@millennium.stealth.net ; Reply for user syrk
     */
    USERHOST("USERHOST"),

    /**
     * Command: ISON
     *
     * Parameters: `<nickname> *( SPACE <nickname> )`
     *
     * The ISON command was implemented to provide a quick and efficient
     * means to get a response about whether a given nickname was currently
     * on IRC. ISON only takes one (1) type of parameter: a space-separated
     * list of nicks. For each nickname in the list that is present, the
     * server adds that to its reply string. Thus the reply string may
     * return empty (none of the given nicks are present), an exact copy of
     * the parameter string (all of them present) or any other subset of the
     * set of nicks given in the parameter. The only limit on the number of
     * nicks that may be checked is that the combined length MUST NOT be too
     * large as to cause the server to chop it off so it fits in 512
     * characters.
     *
     * ISON is only processed by the server local to the client sending the
     * command and thus not passed onto other servers for further
     * processing.
     *
     * Numeric Replies:
     *
     * * [RPL_ISON]
     * * [ERR_NEEDMOREPARAMS]
     *
     * Example:
     *
     * * ISON phone trillian WiZ jarlek Avalon Angel Monstah syrk ; Sample ISON request for 7 nicks.
     */
    ISON("ISON"),
    /**
     * One of the replies that is sent back by the server after successful authentication
     *
     * Example:
     * * 001 Welcome to the Internet Relay Network `<nick>!<user>@<host>`
     * @see RPL_YOURHOST
     * @see RPL_CREATED
     * @see RPL_MYINFO
     * @see RPL_ISUPPRORT
     */
    RPL_WELCOME("001"),

    /**
     * One of the replies that is sent back by the server after successful authentication
     *
     * 002 Your host is `<servername>`, running version `<ver>`
     * @see RPL_WELCOME
     * @see RPL_CREATED
     * @see RPL_MYINFO
     * @see RPL_ISUPPRORT
     */
    RPL_YOURHOST("002"),

    /**
     * One of the replies that is sent back by the server after successful authentication
     *
     * Example:
     * * 003 This server was created `<date>`
     *
     * @see RPL_WELCOME
     * @see RPL_YOURHOST
     * @see RPL_MYINFO
     * @see RPL_ISUPPRORT
     */
    RPL_CREATED("003"),

    /**
     * One of the replies that is sent back by the server after successful authentication
     *
     * 004 `<servername> <version> <available user modes> <available channel modes>`
     * @see RPL_WELCOME
     * @see RPL_YOURHOST
     * @see RPL_CREATED
     * @see RPL_ISUPPRORT
     */
    RPL_MYINFO("004"),

    /**
     * One of the replies that is sent back by the server after successful authentication.
     *
     * According RFC 2812, 005 means RPL_BOUNCE, but Undernet and Dalnet servers pushed a de facto
     * standard to use it as RPL_ISUPPORT code, which notes what modes server supports in particular.
     *
     * Though there is no official document on RPL_ISUPPORT, you can read about it in Brocklesby's
     * IRC support draft at [irc.org](http://www.irc.org/tech_docs/draft-brocklesby-irc-isupport-03.txt)
     */
    RPL_ISUPPRORT("005"),

    /**
     * Sent by the server to a user upon connection to indicate
     * the restricted nature of the connection (user mode "+r").
     *
     * Example:
     * * :Your connection is restricted!
     */
    ERR_RESTRICTED("484"),

    /**
     * [RPL_YOUREOPER] is sent back to a client which has
     * just successfully issued an [Command.OPER] message and gained
     * operator status.
     *
     * Example:
     * * 381 :You are now an IRC operator
     */
    RPL_YOUREOPER("381"),

    /**
     * If a client sends an [Command.OPER] message and the server has
     * not been configured to allow connections from the
     * client's host as an operator, this error MUST be
     * returned.
     *
     * Example:
     * * :No O-lines for your host
     */
    ERR_NOOPERHOST("491"),

    /**
     * Error sent to any user trying to view or change the
    user mode for a user other than themselves.
     * Example:
     * * :Cannot change mode for other users
     */
    ERR_USERSDONTMATCH("502"),

    /**
     * Returned by the server to indicate that a [Command.MODE]
     * message was sent with a nickname parameter and that
     * the a mode flag sent was not recognized.
     *
     * Example:
     * * :Unknown MODE flag
     */
    ERR_UMODEUNKNOWNFLAG("501"),

    /**
     * To answer a query about a client's own mode, RPL_UMODEIS is sent back.
     *
     * Example:
     * `<user mode string>`
     */
    RPL_UMODEIS("221"),

    /**
     * Any command requiring operator privileges to operate
     * MUST return this error to indicate the attempt was
     * unsuccessful.
     *
     * Example:
     * * :Permission Denied- You're not an IRC operator
     */
    ERR_NOPRIVILEGES("481"),

    /**
     * Used to indicate the server name given currently does not exist.
     * Example:
     * * `<server name> :No such server`
     */
    ERR_NOSUCHSERVER("402"),

    /**
     * Used to indicate the given channel name is invalid.
     *
     * Example:
     * * `<channel name> :No such channel`
     */
    ERR_NOSUCHCHANNEL("403"),

    /**
     * Sent to a user when they have joined the maximum
     * number of allowed channels and they try to join
     * another channel.
     *
     * Example:
     * * `<channel name> :You have joined too many channels`
     */
    ERR_TOOMANYCHANNELS("405"),

    /**
     * According to error response codes, this code is returned in 3 cases:
     * * Returned to a client which is attempting to send a [Command.PRIVMSG]/[Command.NOTICE] using the
     * user@host destination format and for a user@host which has several occurrences.
     * * Returned to a client which trying to send a [Command.PRIVMSG]/[Command.NOTICE] to too many recipients.
     * * Returned to a client which is attempting to [Command.JOIN] a safe channel using the shortname when
     * there are more than one such channel.
     *
     * Example:
     * * `<target> :<error code> recipients. <abort message>`
     */
    ERR_TOOMANYTARGETS("407"),

    /**
     * When sending a [Command.TOPIC] message to determine the
     * channel topic, one of two replies is sent.  If
     * the topic is set, [RPL_TOPIC] is sent back else
     * [RPL_NOTOPIC].
     *
     * Example:
     * * 332 `<channel> :<topic>`
     */
    RPL_TOPIC("332"),

    /**
     * Even though this is undocumented, topic metadata is sent under code 333
     */
    RPL_TOPICBY("333"),

    /**
     * Any command requiring 'chanop' privileges (such as
     * MODE messages) MUST return this error if the client
     * making the attempt is not a chanop on the specified
     * channel.
     *
     * Example:
     * * `<channel> :You're not channel operator`
     */
    ERR_CHANOPRIVSNEEDED("482"),

    /**
     * Example:
     * * 324 `<channel> <mode> <mode params>`
     */
    RPL_CHANNELMODEIS("324"),

    /**
     * When listing the active 'bans' for a given channel,
     * a server is required to send the list back using the
     * [RPL_BANLIST] and [RPL_ENDOFBANLIST] messages.  A separate
     * [RPL_BANLIST] is sent for each active banmask.  After the
     * banmasks have been listed (or if none present) a
     * [RPL_ENDOFBANLIST] MUST be sent.
     *
     * Example:
     * * 367 `<channel> <banmask>`
     */
    RPL_BANLIST("367"),

    /**
     * Example:
     * * 368 `<channel>` :End of channel ban list`
     *
     * @see RPL_BANLIST
     */
    RPL_ENDOFBANLIST("368"),

    /**
     * Example:
     * * 348 `<channel> <exceptionmask>`
     */
    RPL_EXCEPTLIST("348"),

    /**
     * Example:
     * * 349 `<channel>` :End of channel exception list
     * @see RPL_EXCEPTLIST
     */
    RPL_ENDOFEXCEPTLIST("349"),

    /**
     * When listing the 'invitations masks' for a given channel,
     * a server is required to send the list back using the
     * [RPL_INVITELIST] and [RPL_ENDOFINVITELIST] messages.  A
     * separate [RPL_INVITELIST] is sent for each active mask.
     * After the masks have been listed (or if none present) a
     * [RPL_ENDOFINVITELIST] MUST be sent.
     *
     * Example:
     * * 346 `<channel> <invitemask>`
     */
    RPL_INVITELIST("346"),

    /**
     * Example:
     * * 347 `<channel>` :End of channel invite list
     * @see RPL_INVITELIST
     */
    RPL_ENDOFINVITELIST("347"),

    /**
     * Example:
     * * 325 `<channel> <nickname>`
     */
    RPL_UNIQOPIS("325"),

    /**
     * When sending a [Command.TOPIC] message to determine the
     * channel topic, one of two replies is sent.  If
     * the topic is set, [RPL_TOPIC] is sent back else
     * [RPL_NOTOPIC].
     * Example:
     * * 331 `<channel>` :No topic is set
     */
    RPL_NOTOPIC("331"),

//ERR_TOOMANYMATCHES = ""

    /**
     * "@" is used for secret channels, "*" for private
     * channels, and "=" for others (public channels).
     *
     * To reply to a [Command.NAMES] message, a reply pair consisting
     * of [RPL_NAMEREPLY] and [RPL_ENDOFNAMES] is sent by the
     * server back to the client.  If there is no channel
     * found as in the query, then only [RPL_ENDOFNAMES] is
     * returned.  The exception to this is when a [Command.NAMES]
     * message is sent with no parameters and all visible
     * channels and contents are sent back in a series of
     * [RPL_NAMEREPLY] messages with a [RPL_ENDOFNAMES] to mark
     * the end.
     *
     * Example:
     * * `( "=" / "*" / "@" ) <channel> :[ "@" / "+" ] <nick> *( " " [ "@" / "+" ] <nick> )`
     */
    RPL_NAMEREPLY("353"),

    /**
     * Example:
     * * 366 `<channel>` :End of NAMES list
     * @see RPL_NAMEREPLY
     */
    RPL_ENDOFNAMES("366"),

    /**
     * Replies [RPL_LIST], [RPL_LISTEND] mark the actual replies
     * with data and end of the server's response to a [Command.LIST]
     * command.  If there are no channels available to return,
     * only the end reply MUST be sent.
     *
     * Example:
     * * 322 `<channel> <# visible> :<topic>`
     */
    RPL_LIST("322"),

    /**
     * Replies [RPL_LIST], [RPL_LISTEND] mark the actual replies
     * with data and end of the server's response to a [Command.LIST]
     * command.  If there are no channels available to return,
     * only the end reply MUST be sent.
     *
     * Example:
     * * 323 :End of LIST
     */
    RPL_LISTEND("323"),

    /**
     * Used to indicate the nickname parameter supplied to a command is currently unused.
     * Example:
     * * "<nickname> :No such nick/channel"
     */
    ERR_NOSUCHNICK("401"),

    /**
     * Returned by the server to indicate that the
     * attempted [Command.INVITE] message was successful and is
     * being passed onto the end client.
     *
     * Example:
     * * 341 `<nick> <channel>`
     *
     * Note: Example fixed to follow the errata.
     */
    RPL_INVITING("341"),

    /**
     * These replies are used with the [Command.AWAY] command (if
     * allowed).  [RPL_AWAY] is sent to any client sending a
     * [Command.PRIVMSG] to a client which is away.  [RPL_AWAY] is only
     * sent by the server to which the client is connected.
     * Replies [RPL_UNAWAY] and [RPL_NOWAWAY] are sent when the
     * client removes and sets an [Command.AWAY] message.
     * Example:
     * * 301 `<nick> :<away message>`
     */
    RPL_AWAY("301"),

    /**
     * Sent to a user who is either (a) not on a channel
     * which is mode +n or (b) not a chanop (or mode +v) on
     * a channel which has mode +m set or where the user is
     * banned and is trying to send a PRIVMSG message to
     * that channel.
     *
     * Example:
     * * `<channel name> :Cannot send to channel`
     */
    ERR_CANNOTSENDTOCHAN("404"),

    /**
     * When responding to the [Command.MOTD] message and the [Command.MOTD] file
     * is found, the file is displayed line by line, with
     * each line no longer than 80 characters, using
     * [RPL_MOTD] format replies.  These MUST be surrounded
     * by a [RPL_MOTDSTART] (before the [RPL_MOTD]s) and an
     * [RPL_ENDOFMOTD] (after).
     *
     * Example:
     * * 375 :- `<server>` Message of the day -
     */
    RPL_MOTDSTART("375"),

    /**
     * Example:
     * * 372 :- `<text>`
     * @see RPL_MOTDSTART
     */
    RPL_MOTD("372"),

    /**
     * Example:
     * * 376 :End of MOTD command
     * @see RPL_MOTDSTART
     */
    RPL_ENDOFMOTD("376"),

    /**
     * Server's MOTD file could not be opened by the server.
     *
     * Example:
     * * 422 :MOTD File is missing
     */
    ERR_NOMOTD("422"),

    /**
     * Reply by the server showing its version details.
     * The `<version>` is the version of the software being
     * used (including any patchlevel revisions) and the
     * `<debuglevel>` is used to indicate if the server is
     * running in "debug mode".

     * The "comments" field may contain any comments about
     * the version or further version details.
     *
     * Example:
     * * 351 `<version>.<debuglevel> <server> :<comments>`
     */
    RPL_VERSION("351"),

    /**
     * reports statistics on a connection.  `<linkname>`
     * identifies the particular connection, `<sendq>` is
     * the amount of data that is queued and waiting to be
     * sent `<sent messages>` the number of messages sent,
     * and `<sent Kbytes>` the amount of data sent, in
     * Kbytes. `<received messages>` and `received Kbytes>`
     * are the equivalent of `<sent messages>` and
     * `<sent Kbytes>` for received data, respectively.
     * `<time open>` indicates how long ago the connection was
     * opened, in seconds.
     *
     * Example:
     * * `<linkname> <sendq> <sent messages> <sent Kbytes> <received messages> <received Kbytes> <time open>`
     */
    RPL_STATSLINKINFO("211"),

    /**
     * reports the server uptime
     *
     * Example:
     * * :Server Up %d days %d:%02d:%02d
     */
    RPL_STATSUPTIME("242"),

    /**
     * reports statistics on commands usage
     *
     * Example:
     * * `<command> <count> <byte count> <remote count>`
     */
    RPL_STATSCOMMANDS("212"),

    /**
     * reports the allowed hosts from where user may become IRC operators
     *
     * Example:
     * * O `<hostmask> * <name>`
     */
    RPL_STATSOLINE("243"),

    /**
     * Example:
     * * `<stats letter>` :End of STATS report
     */
    RPL_ENDOFSTATS("219"),

    /**
     * Sent by the server to a service upon successful registration.
     *
     * Example:
     * * 383 :You are service `<servicename>`
     *
     * Note: the example should have start of long argument marker (:) though it doesn't in RFC
     */
    RPL_YOURESERVICE("383"),

    /**
     * Reply format used by [Command.USERHOST] to list replies to the query list.  The reply string is
     * composed as follows:
     * * `reply = nickname [ "*" ] "=" ( "+" / "-" ) hostname`
     *
     * The '*' indicates whether the client has registered
     * as an Operator.  The '-' or '+' characters represent
     * whether the client has set an AWAY message or not
     * respectively.
     *
     * Example:
     * * `:*1<reply> *( " " <reply> )`
     */
    RPL_USERHOST("302"),
    /**
     * Reply format used by [Command.ISON] to list replies to the
     * query list.
     *
     * Example:
     * * 303 `:*1<nick> *( " " <nick> )`
     */
    RPL_ISON("303"),

    /**
     * These replies are used with the [Command.AWAY] command (if
     * allowed).  [RPL_AWAY] is sent to any client sending a
     * [Command.PRIVMSG] to a client which is away.  [RPL_AWAY] is only
     * sent by the server to which the client is connected.
     * Replies RPL_UNAWAY and [RPL_NOWAWAY] are sent when the
     * client removes and sets an [Command.AWAY] message.
     *
     * Example:
     * * 305 :You are no longer marked as being away
     *
     * @see RPL_AWAY
     * @see RPL_NOWAWAY
     */
    RPL_UNAWAY("305"),

    /**
     * These replies are used with the [Command.AWAY] command (if
     * allowed).  [RPL_AWAY] is sent to any client sending a
     * [Command.PRIVMSG] to a client which is away.  [RPL_AWAY] is only
     * sent by the server to which the client is connected.
     * Replies [RPL_UNAWAY] and RPL_NOWAWAY are sent when the
     * client removes and sets an [Command.AWAY] message.
     *
     * Example:
     * * 306 :You have been marked as being away
     *
     * @see RPL_UNAWAY
     * @see RPL_AWAY
     */
    RPL_NOWAWAY("306"),

    /**
     * Replies 311 - 313, 317 - 319 are all replies
     * generated in response to a [Command.WHOIS] message.  Given that
     * there are enough parameters present, the answering
     * server MUST either formulate a reply out of the above
     * numerics (if the query nick is found) or return an
     * error reply.  The '*' in [RPL_WHOISUSER] is there as
     * the literal character and not as a wild card.  For
     * each reply set, only [RPL_WHOISCHANNELS] may appear
     * more than once (for long lists of channel names).
     * The '@' and '+' characters next to the channel name
     * indicate whether a client is a channel operator or
     * has been granted permission to speak on a moderated
     * channel.  The [RPL_ENDOFWHOIS] reply is used to mark
     * the end of processing a [Command.WHOIS] message.
     *
     * Example:
     * * 311 `<nick> <user> <host> * :<real name>`
     */
    RPL_WHOISUSER("311"),

    /**
     * @see RPL_WHOISUSER
     * Example:
     * * 312 `<nick> <server> :<server info>`
     */
    RPL_WHOISSERVER("312"),

    /**
     * Example:
     * * 313 `<nick>` :is an IRC operator
     * @see RPL_WHOISUSER
     */
    RPL_WHOISOPERATOR("313"),

    /**
     * @see RPL_WHOISUSER
     * Example:
     * * 317 `<nick> <integer>` :seconds idle
     */
    RPL_WHOISIDLE("317"),

    /**
     * @see RPL_WHOISUSER
     * Example:
     * * 318 `<nick>` :End of WHOIS list
     */
    RPL_ENDOFWHOIS("318"),

    /**
     * @see RPL_WHOISUSER
     * Example:
     * * 319 `<nick> :*( ( "@" / "+" ) <channel> " " )`
     */
    RPL_WHOISCHANNELS("319"),

    /**
     * When replying to a [Command.WHOWAS] message, a server MUST use
     * the replies [RPL_WHOWASUSER], [RPL_WHOISSERVER] or
     * [ERR_WASNOSUCHNICK] for each nickname in the presented
     * list.  At the end of all reply batches, there MUST
     * be [RPL_ENDOFWHOWAS] (even if there was only one reply
     * and it was an error).
     * Example:
     * * 314 `<nick> <user> <host> * :<real name>`
     */
    RPL_WHOWASUSER("314"),

    /**
     * Example:
     * * 369 `<nick>` :End of WHOWAS
     * @see RPL_WHOWASUSER
     */
    RPL_ENDOFWHOWAS("369"),

    /**
     * Returned by a server answering a [Command.SUMMON] message to
     * indicate that it is summoning that user.
     *
     * Example:
     * * 342 `<user> :Summoning user to IRC
     */
    RPL_SUMMONING("342"),

    /**
     * The [RPL_WHOREPLY] and [RPL_ENDOFWHO] pair are used
     * to answer a WHO message.  The [RPL_WHOREPLY] is only
     * sent if there is an appropriate match to the WHO
     * query.  If there is a list of parameters supplied
     * with a [Command.WHO] message, a [RPL_ENDOFWHO] MUST be sent
     * after processing each list item with `<name>` being
     * the item.
     *
     * Example:
     * * 352 `<channel> <user> <host> <server> <nick> ( "H" / "G" ) ["*"] [ ( "@" / "+" ) ] :<hopcount> <real name>`
     */
    RPL_WHOREPLY("352"),

    /**
     * Example:
     * * 315 `<name> :End of WHO list`
     * @see RPL_WHOREPLY
     */
    RPL_ENDOFWHO("315"),

    /**
     * In replying to the [Command.LINKS] message, a server MUST send
     * replies back using the [RPL_LINKS] numeric and mark the
     * end of the list using an [RPL_ENDOFLINKS] reply.
     *
     * Example:
     * * 364 `<mask> <server> :<hopcount> <server info>`
     */
    RPL_LINKS("364"),

    /**
     * Example:
     * * 365 `<mask> :End of LINKS list`
     * @see RPL_LINKS
     */
    RPL_ENDOFLINKS("365"),

    /**
     * A server responding to an [Command.INFO] message is required to
     * send all its 'info' in a series of [RPL_INFO] messages
     * with a [RPL_ENDOFINFO] reply to indicate the end of the
     * replies.
     *
     * Example:
     * * 371 :`<string>`
     */
    RPL_INFO("371"),

    /**
     * Example:
     * * 374 :End of INFO list
     * @see RPL_INFO
     */
    RPL_ENDOFINFO("374"),

    /**
     * If the [Command.REHASH] option is used and an operator sends
     * a [Command.REHASH] message, an RPL_REHASHING is sent back to
     * the operator.
     *
     * Example:
     * * 382 `<config file>` :Rehashing
     */
    RPL_REHASHING("382"),

    /**
     * When replying to the [Command.TIME] message, a server MUST send
     * the reply using the RPL_TIME format below.  The string
     * showing the time need only contain the correct day and
     * time there.  There is no further requirement for the
     * time string.
     *
     * Example:
     * * 391 `<server> :<string showing server's local time>`
     */
    RPL_TIME("391"),

    /**
     * If the [Command.USERS] message is handled by a server, the
     * replies [RPL_USERSTART], [RPL_USERS], [RPL_ENDOFUSERS] and
     * [RPL_NOUSERS] are used.  [RPL_USERSSTART] MUST be sent
     * first, following by either a sequence of RPL_USERS
     * or a single [RPL_NOUSER].  Following this is
     * [RPL_ENDOFUSERS].
     *
     * Example:
     * * 392 :UserID   Terminal  Host
     */
    RPL_USERSSTART("392"),

    /**
     * Example:
     * * 393 :`<username> <ttyline> <hostname>`
     * @see RPL_USERSSTART
     */
    RPL_USERS("393"),

    /**
     * Example:
     * * 394 :End of users
     * @see RPL_USERSSTART
     */
    RPL_ENDOFUSERS("394"),

    /**
     * Example:
     * * 395 :Nobody logged in
     * @see RPL_USERSSTART
     */
    RPL_NOUSERS("395"),

    /**
     * The RPL_TRACE* are all returned by the server in
     * response to the [Command.TRACE] message.  How many are
     * returned is dependent on the TRACE message and
     * whether it was sent by an operator or not.  There
     * is no predefined order for which occurs first.
     * Replies [RPL_TRACEUNKNOWN], [RPL_TRACECONNECTING] and
     * [RPL_TRACEHANDSHAKE] are all used for connections
     * which have not been fully established and are either
     * unknown, still attempting to connect or in the
     * process of completing the 'server handshake'.
     * [RPL_TRACELINK] is sent by any server which handles
     * a TRACE message and has to pass it on to another
     * server.  The list of [RPL_TRACELINKs] sent in
     * response to a TRACE command traversing the IRC
     * network should reflect the actual connectivity of
     * the servers themselves along that path.
     * [RPL_TRACENEWTYPE] is to be used for any connection
     * which does not fit in the other categories but is
     * being displayed anyway.
     * [RPL_TRACEEND] is sent to indicate the end of the list.
     *
     * Example:
     * * `Link <version & debug level> <destination> <next server> V<protocol version> <link uptime in seconds> <backstream sendq> <upstream sendq>`
     */
    RPL_TRACELINK("200"),

    /**
     * Example:
     * * `Try. <class> <server>`
     * @see RPL_TRACELINK
     */
    RPL_TRACECONNECTING("201"),

    /**
     * Example:
     * * `H.S. <class> <server>`
     * @see RPL_TRACELINK
     */
    RPL_TRACEHANDSHAKE("202"),

    /**
     * Example:
     * * `???? <class> [<client IP address in dot form>]`
     * @see RPL_TRACELINK
     */
    RPL_TRACEUNKNOWN("203"),

    /**
     * Example:
     * * `Oper <class> <nick>`
     * @see RPL_TRACELINK
     */
    RPL_TRACEOPERATOR("204"),

    /**
     * Example:
     * * `User <class> <nick>`
     * @see RPL_TRACELINK
     */
    RPL_TRACEUSER("205"),

    /**
     * Example:
     * * `Serv <class> <int>S <int>C <server> <nick!user|*!*>@<host|server> V<protocol version>`
     * @see RPL_TRACELINK
     */
    RPL_TRACESERVER("206"),

    /**
     * Example:
     * * `Service <class> <name> <type> <active type>`
     * @see RPL_TRACELINK
     */
    RPL_TRACESERVICE("207"),

    /**
     * Example:
     * * `<newtype> 0 <client name>`
     * @see RPL_TRACELINK
     */
    RPL_TRACENEWTYPE("208"),

    /**
     * Example:
     * * `Class <class> <count>`
     * @see RPL_TRACELINK
     */
    RPL_TRACECLASS("209"),

    /**
     * Example:
     * * `File <logfile> <debug level>`
     * @see RPL_TRACELINK
     */
    RPL_TRACELOG("261"),

    /**
     * Example:
     * * `<server name> <version & debug level> :End of TRACE`
     * @see RPL_TRACELINK
     */
    RPL_TRACEEND("262"),

    /**
     * When listing services in reply to a [Command.SERVLIST] message,
     * a server is required to send the list back using the
     * RPL_SERVLIST and [RPL_SERVLISTEND] messages.  A separate
     * RPL_SERVLIST is sent for each service.  After the
     * services have been listed (or if none present) a
     * [RPL_SERVLISTEND] MUST be sent.
     * Example:
     * * 234 `<name> <server> <mask> <type> <hopcount> <info>`
     */
    RPL_SERVLIST("234"),


    /**
     * Example:
     * * 235 `<mask> <type> :End of service listing`
     * @see RPL_SERVLIST
     */
    RPL_SERVLISTEND("235"),

    /**
     * In processing an [Command.LUSERS] message, the server
     * sends a set of replies from RPL_LUSERCLIENT,
     * [RPL_LUSEROP], [RPL_USERUNKNOWN],
     * [RPL_LUSERCHANNELS] and [RPL_LUSERME].  When
     * replying, a server MUST send back
     * RPL_LUSERCLIENT and [RPL_LUSERME].  The other
     * replies are only sent back if a non-zero count
     * is found for them.
     *
     * Example:
     * * `:There are <integer> users and <integer> services on <integer> servers`
     */
    RPL_LUSERCLIENT("251"),


    /**
     * Example:
     * * `<integer> :operator(s) online`
     * @see RPL_LUSERCLIENT
     */

    RPL_LUSEROP("252"),

    /**
     * Example:
     * * `<integer> :unknown connection(s)`
     * @see RPL_LUSERCLIENT
     */
    RPL_LUSERUNKNOWN("253"),

    /**
     * Example:
     * * `<integer> :channels formed`
     * @see RPL_LUSERCLIENT
     */
    RPL_LUSERCHANNELS("254"),

    /**
     * Example:
     * * `:I have <integer> clients and <integer`
    servers"
     * @see RPL_LUSERCLIENT
     */
    RPL_LUSERME("255"),

    /**
     * When replying to an [Command.ADMIN] message, a server
     * is expected to use replies RPL_ADMINME
     * through to [RPL_ADMINEMAIL] and provide a text
     * message with each.  For [RPL_ADMINLOC1] a
     * description of what city, state and country
     * the server is in is expected, followed by
     * details of the institution ([RPL_ADMINLOC2])
     * and finally the administrative contact for the
     * server (an email address here is REQUIRED)
     * in [RPL_ADMINEMAIL].
     * Example:
     * * `<server> :Administrative info`
     */
    RPL_ADMINME("256"),

    /**
     * Example:
     * * `:<admin info>`
     * @see RPL_ADMINME
     */
    RPL_ADMINLOC1("257"),

    /**
     * Example:
     * * `:<admin info>`
     * @see RPL_ADMINME
     */
    RPL_ADMINLOC2("258"),

    /**
     * Example:
     * * `:<admin info>`
     * @see RPL_ADMINME
     */
    RPL_ADMINEMAIL("259"),

    /**
     * When a server drops a command without processing it,
     * it MUST use the reply RPL_TRYAGAIN to inform the
     * originating client.
     *
     * Example:
     * * `<command> :Please wait a while and try again.`
     */
    RPL_TRYAGAIN("263"),

    /**
     * Returned by [Command.WHOWAS] to indicate there is no history information for that nickname.
     *
     * Example:
     * * `<nickname>` :There was no such nickname
     */
    ERR_WASNOSUCHNICK("406"),

    /**
     * Returned to a client which is attempting to send a [Command.SQUERY] to a service which does not exist.
     *
     * Example:
     * * `<service name> :No such service`
     */
    ERR_NOSUCHSERVICE("408"),

    /**
     * [Command.PING] or [Command.PONG] message missing the originator parameter.
     *
     * Example:
     * * :No origin specified
     */
    ERR_NOORIGIN("409"),

    /**
     * Example:
     * * `:No recipient given (<command>)`
     * @see ERR_NOTEXTTOSEND
     */
    ERR_NORECIPIENT("411"),

    /**
     *
     * 411 - 415 are returned by [Command.PRIVMSG] to indicate that
     * the message wasn't delivered for some reason.
     * [ERR_NOTOPLEVEL] and [ERR_WILDTOPLEVEL] are errors that
     * are returned when an invalid use of
     * "PRIVMSG $<server>" or "PRIVMSG #<host>" is attempted.
     *
     * Example:
     * * `:No text to send`
     */
    ERR_NOTEXTTOSEND("412"),

    /**
     * Example:
     * * `<mask> :No toplevel domain specified`
     * @see ERR_NOTEXTTOSEND
     */
    ERR_NOTOPLEVEL("413"),

    /**
     * Example:
     * * `<mask> :Wildcard in toplevel domain`
     * @see ERR_NOTEXTTOSEND
     */
    ERR_WILDTOPLEVEL("414"),

    /**
     * Example:
     * * `<mask> :Bad Server/host mask`
     * @see ERR_NOTEXTTOSEND
     */
    ERR_BADMASK("415"),

    /**
     * Returned to a registered client to indicate that the command sent is unknown by the server.
     *
     * Example:
     * * `<command> :Unknown command`
     */
    ERR_UNKNOWNCOMMAND("421"),

    /**
     * Any [Command.MODE] requiring "channel creator" privileges MUST
     * return this error if the client making the attempt is not
     * a chanop on the specified channel.
     *
     * Example:
     * * :You're not the original channel operator
     */
    ERR_UNIQOPPRIVSNEEDED("485"),

    /**
     * Any attempts to use the KILL command on a server are to be refused and this error returned directly to the client.
     *
     * Example:
     * * :You can't kill a server!
     */
    ERR_CANTKILLSERVER("483"),

    /**
     * Example:
     * * `<channel> :Channel key already set`
     */
    ERR_KEYSET("467"),

    /**
     * Example:
     * * `<channel> :Cannot join channel (+l)`
     */
    ERR_CHANNELISFULL("471"),

    /**
     * Example:
     * * `<char> :is unknown mode char to me for <channel>`
     */
    ERR_UNKNOWNMODE("472"),

    /**
     * Example:
     * * `<channel> :Cannot join channel (+i)`
     */
    ERR_INVITEONLYCHAN("473"),

    /**
     * Example:
     * * `<channel> :Cannot join channel (+b)`
     */
    ERR_BANNEDFROMCHAN("474"),

    /**
     * Example:
     * * `<channel> :Cannot join channel (+k)`
     */
    ERR_BADCHANNELKEY("475"),

    /**
     * Example:
     * * `<channel> :Bad Channel Mask`
     */
    ERR_BADCHANMASK("476"),

    /**
     * Example:
     * * `<channel> :Channel doesn't support modes`
     */
    ERR_NOCHANMODES("477"),

    /**
     * Example:
     * * `<channel> <char> :Channel list is full`
     */
    ERR_BANLISTFULL("478"),

    /**
     * Returned by a server in response to an ADMIN message
     * when there is an error in finding the appropriate
     * information.
     *
     * Example:
     * * `<server> :No administrative info available`
     */
    ERR_NOADMININFO("423"),

    /**
     * Generic error message used to report a failed file
     * operation during the processing of a message.
     *
     * Example:
     * * `:File error doing <file op> on <file>`
     */
    ERR_FILEERROR("424"),

    /**
     * Returned when a nickname parameter expected for a
     * command and isn't found.
     *
     * Example:
     * * `:No nickname given`
     */
    ERR_NONICKNAMEGIVEN("431"),


    /**
     * Returned after receiving a [Command.NICK] message which contains
     * characters which do not fall in the defined set.  See
     * section 2.3.1 for details on valid nicknames.
     *
     * Example:
     * * `<nick> :Erroneous nickname`
     */
    ERR_ERRONEUSNICKNAME("432"),

    /**
     * Returned when a [Command.NICK] message is processed that results
     * in an attempt to change to a currently existing
     * nickname.
     *
     * Example:
     * * `<nick> :Nickname is already in use`
     */
    ERR_NICKNAMEINUSE("433"),
    /**
     * Returned by a server to a client when it detects a
     * nickname collision (registered of a [Command.NICK] that
     * already exists by another server).
     *
     * Example:
     * * `<nick> :Nickname collision KILL from <user>@<host>`
     */
    ERR_NICKCOLLISION("436"),

    /**
     * Returned by a server to a user trying to join a channel
     * currently blocked by the channel delay mechanism.
     *
     * Returned by a server to a user trying to change nickname
     * when the desired nickname is blocked by the nick delay
     * mechanism.
     *
     * Example:
     * * `<nick/channel> :Nick/channel is temporarily unavailable`
     */
    ERR_UNAVAILRESOURCE("437"),


    /**
     * Returned by the server to indicate that the target
     * user of the command is not on the given channel.
     *
     * Example:
     * * `<nick> <channel> :They aren't on that channel`
     */
    ERR_USERNOTINCHANNEL("441"),


    /**
     * Returned by the server whenever a client tries to
     * perform a channel affecting command for which the
     * client isn't a member.
     * Example:
     * * `<channel> :You're not on that channel`
     */
    ERR_NOTONCHANNEL("442"),


    /**
     * Returned when a client tries to invite a user to a
     * channel they are already on.
     *
     * Example:
     * * `<user> <channel> :is already on channel`
     */
    ERR_USERONCHANNEL("443"),


    /**
     * Returned by the summon after a SUMMON command for a
     * user was unable to be performed since they were not
     * logged in.
     *
     * Example:
     * * `<user> :User not logged in`
     */
    ERR_NOLOGIN("444"),


    /**
     * Returned as a response to the SUMMON command.  MUST be
     * returned by any server which doesn't implement it.
     *
     * Example:
     * * `:SUMMON has been disabled`
     */
    ERR_SUMMONDISABLED("445"),


    /**
     * Returned as a response to the USERS command.  MUST be
     * returned by any server which does not implement it.
     *
     * Example:
     * * `:USERS has been disabled`
     */
    ERR_USERSDISABLED("446"),


    /**
     * Returned by the server to indicate that the client
     * MUST be registered before the server will allow it
     * to be parsed in detail.
     *
     * Example:
     * * `:You have not registered`
     */
    ERR_NOTREGISTERED("451"),


    /**
     * Returned by the server by numerous commands to
     * indicate to the client that it didn't supply enough
     * parameters.
     *
     * Example:
     * * `<command> :Not enough parameters`
     */
    ERR_NEEDMOREPARAMS("461"),


    /**
     * Returned by the server to any link which tries to
     * change part of the registered details (such as
     * password or user details from second USER message).
     *
     * Example:
     * * `:Unauthorized command (already registered)`
     */
    ERR_ALREADYREGISTRED("462"),


    /**
     * Returned to a client which attempts to register with
     * a server which does not been setup to allow
     * connections from the host the attempted connection
     * is tried.
     *
     * Example:
     * * `:Your host isn't among the privileged`
     */
    ERR_NOPERMFORHOST("463"),


    /**
     * Returned to indicate a failed attempt at registering
     * a connection for which a password was required and
     * was either not given or incorrect.
     *
     * Example:
     * * `:Password incorrect`
     */
    ERR_PASSWDMISMATCH("464"),


    /**
     * Returned after an attempt to connect and register
     * yourself with a server which has been setup to
     * explicitly deny connections to you.
     *
     * Example:
     * * `:You are banned from this server`
     */
    ERR_YOUREBANNEDCREEP("465"),

    /**
     * Sent by a server to a user to inform that access to the server will soon be denied.
     */
    ERR_YOUWILLBEBANNED("466"),

    /**
     * Servers have a tendency of sending NOTICE AUTH responses upon connecting to server meaning
     * that the connection is successful and that they can begin the authentication process defined
     * in RFC 2812 3.1
     *
     * 3.1 Connection Registration
     *
     *  The commands described here are used to register a connection with an
     *  IRC server as a user as well as to correctly disconnect.
     *
     *  A "PASS" command is not required for a client connection to be
     *  registered, but it MUST precede the latter of the NICK/USER
     *  combination (for a user connection) or the SERVICE command (for a
     *  service connection). The RECOMMENDED order for a client to register
     *  is as follows:
     *
     *  1. Pass message
     *  2. Nick message                 2. Service message
     *  3. User message
     *
     * Upon success, the client will receive an RPL_WELCOME (for users) or
     * RPL_YOURESERVICE (for services) message indicating that the
     * connection is now registered and known the to the entire IRC network.
     * The reply message MUST contain the full client identifier upon which
     * it was registered.
     */
    AUTH("AUTH")
}