Project - COMP3004 APPropriately Moist Project
Author - Matthew Moulton (101010631)

Citations: based code off of Prof. Runka's in-class examples from COMP2406

Running Instructions: From the ChatServer directory, run "node server.js"
                      Access the server from your.ip.address:2406

Android App Info:
- You ONLY need to care about the 'server.js' file for Android purposes
- Connects to your.server.ip.address:2406
- All "data" sent between server and client are just plain strings
- Server will store name="data" as a chat user upon receiving an "intro" event [ie. socket.emit("intro","bob")]
- Server will send/receive messages as a "message" event
- "connection" and "disconnect" events are handled internally by socket.io upon receiving/losing a connection
