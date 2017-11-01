/*
Author - Matthew Moulton (101010631)
COMP3004 APPropriately Moist Project
*/

var http = require('http').createServer(handler); //http portion of server only used for web browser connections (not phone app)
var io = require('socket.io')(http);
var fs = require('fs');
var mime = require('mime-types');
var url = require('url');

const ROOT = "./public_html";
http.listen(2406);
console.log("Chat server listening on port 2406");

// note: if looking at this for the COMP3004 App, this whole HTTP handler is
//       not relevant to the phone side of things, just for testing via browser
function handler(req,res){ //server request handler
	var urlObj = url.parse(req.url,true);
	var filename = ROOT+urlObj.pathname;

	if (urlObj.pathname == "/test"){
		respond(200, "test");
	} else {
	fs.stat(filename,function(err, stats){ //attempt to open file
		if(err){
			respondErr(err);
		} else {
			if(stats.isDirectory())	filename+="/index.html"; //check for directory
			fs.readFile(filename, "utf8", function(err,data){ //read file and send it as a response
				if(err)respondErr(err);
				else respond(200,data);
			});
		}
	});
	}

	//sends off the response message
	function respond(code, data){
		res.writeHead(code, {'content-type': mime.lookup(filename)|| 'text/html'});
		res.end(data);
	}
	//responds in error, and outputs to the console
	function respondErr(err){
		console.log("Handling error: ",err);
		if(err.code==="ENOENT"){
			serve404();
		}else{
			respond(500,err.message);
		}
	}
	//serves 404 files
	function serve404(){
		fs.readFile(ROOT+"/404.html","utf8",function(err,data){ //async
			if(err)respond(500,err.message);
			else respond(404,data);
		});
	}
}; //END http handler

var clients = []; //contains our client socket list
var activeRooms = []; //contains all non-empty chat rooms
var roomNum = 0; //our unique room identifier

io.on("connection", function(socket){ //all socket handling happens in here
	console.log("Got a connection");

	//when a user clicks on the host button, it drops them right into that room
	socket.on("host",function(data){
		room = [];
		room.num = roomNum.toString();
		room.name = data; //data is the name of the room entered by the client
		room.host = socket.username;
		room.users = 1; //we keep track of the # of users remaining in a room
		activeRooms.push(room);
		socket.join(roomNum.toString()); //subscribes this user to the room he created
		socket.room = roomNum.toString();
		console.log("hosted room " + data + " id:" + room.num);
		roomNum++;
	});

	//when the user clicks on 'join' and rooms are displayed to them
	//sends each active room one by one to the phone
	socket.on("roomList",function(data){
		for (var i=0; i<activeRooms.length;i++){
			//the room name and room host fields are delimited by a slash
			socket.emit("room", activeRooms[i].num + " - " + activeRooms[i].name + "/" + activeRooms[i].host);
		}
	});

	//when the user clicks on a specific room to join it
	socket.on("join",function(data){

		console.log("joined room " + data);
		socket.join(data); //subscribe them to that room
		socket.room = data;
		getRoom(data).users++;
		console.log("joined room " + data);
	});

	//logs a name for the current user
	socket.on("login",function(data){
		socket.username = data;
	});

	//introduces new clients to their room
	socket.on("intro",function(data){
		clients.push(socket);
		io.to(socket.room).emit("message", timestamp()+""+socket.username+" has entered the chatroom.");
		//var userList = {};
		//userList.users = getUserList();
		//io.emit("userList", userList);
	});

	//sends a simple chat log message to this user's room
	socket.on("message", function(data){
		console.log("got message: "+data);
		io.to(socket.room).emit("message",timestamp()+""+socket.username+": "+data);
	});

	//lets everyone know when another user disconnects
	socket.on("disconnect", function(){
		console.log(socket.username+" disconnected");
		io.to(socket.room).emit("message", timestamp()+": "+socket.username+" disconnected.");

		//here we make sure to delete the room if it became empty
		rm = getRoom(socket.room);
		if (rm){
		rm.users--;
		if (rm.users == 0){
			activeRooms.splice(getRoomPos(socket.room), getRoomPos(socket.room)+1);
		}
		}

		clients = clients.filter(function(ele){
       return ele!==socket;
		 });

		 //var userList = {};
		 //userList.users = getUserList();
		 //io.emit("userList", userList); //update the userlist
	});

}); //end of socket connection handling

function timestamp(){
	//return new Date().toLocaleTimeString();
	return ""; //looks nicer without the timestamp
}

function getUserList(){ //adds all the current clients names to a list
    var ret = [];
    for(var i=0;i<clients.length;i++){
        ret.push(clients[i].username);
    }
    return ret;
}

//returns the actual room, searched by its unique identifier 'num'
function getRoom(id){
	for (var i=0; i<activeRooms.length;i++){
		if (activeRooms[i].num == id){
			return activeRooms[i];
		}
	}
}

//returns the index of this room in the list
function getRoomPos(id){
	for (var i=0; i<activeRooms.length;i++){
		if (activeRooms[i].num == id){
			return i;
		}
	}
}
