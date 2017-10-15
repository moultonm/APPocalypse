Project - COMP2406 Assignment 3 - Chat Server
Author - Matthew Moulton (101010631)

Running Instructions: From the ChatServer directory, run "node server.js"
                      then from your favorite browser, access "localhost:2406"

Android App Info:
- Connects to your.server.ip.address:2406
- All "data" sent between server and client are just plain strings
- Server will store name="data" as a chat user upon receiving an "intro" event [ie. socket.emit("intro","bob")]
- Server will send/receive messages as a "message" event
- "connection" and "disconnect" events are handled internally by socket.io upon receiving/losing a connection