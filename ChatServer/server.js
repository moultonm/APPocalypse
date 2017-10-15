/*
Author - Matthew Moulton (101010631)
COMP2406, Assignment 3
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

io.on("connection", function(socket){ //all socket handling happens in here
	console.log("Got a connection");
	socket.on("intro",function(data){ //initializes new clients
		socket.username = data;
		socket.blocked = [];
		clients.push(socket);
		socket.broadcast.emit("message", timestamp()+": "+socket.username+" has entered the chatroom.");
		socket.emit("message","Welcome, "+socket.username+".");
		var userList = {};
		userList.users = getUserList();
		io.emit("userList", userList);
	});

	socket.on("message", function(data){ //sends a simple chat log message
		console.log("got message: "+data);
		socket.broadcast.emit("message",timestamp()+", "+socket.username+": "+data);
	});

	socket.on("privateMessage", function(data){ //sends a private message between two users
		console.log("got private message");
		var blocked = false;
		for (var i = 0; i<clients.length; i++){
			if (clients[i].username === data.username){ //find the receiver
				for (var j = 0; j<clients[i].blocked.length; j++){
					if (clients[i].blocked[j] == data.from){ //check if the receiver has the sender blocked
						blocked = true;
					}
				}
				if (!blocked) clients[i].emit("privateMessage", data); //not blocked, send off a PM!
			}
		}
	});

	socket.on("blockUser", function(data){ //one user blocks messages from another
		console.log("got block request");
			for (var i = 0; i<socket.blocked.length; i++){
				if (socket.blocked[i] == data.username){ //see if this user is already blocked
					socket.blocked.splice(i, 1);
					socket.emit("message","User "+data.username+" has been unblocked.");
					return;
				}
			}
			socket.blocked.push(data.username); //if he wasn't blocked, block him
			socket.emit("message","User "+data.username+" has been blocked.");
	});

	socket.on("disconnect", function(){ //lets everyone know when another user disconnects
		console.log(socket.username+" disconnected");
		io.emit("message", timestamp()+": "+socket.username+" disconnected.");

		clients = clients.filter(function(ele){
       return ele!==socket;
		 });

		 var userList = {};
		 userList.users = getUserList();
		 io.emit("userList", userList); //update the userlist
	});

}); //end of socket connection handling

function timestamp(){
	return new Date().toLocaleTimeString();
}

function getUserList(){ //adds all the current clients names to a list
    var ret = [];
    for(var i=0;i<clients.length;i++){
        ret.push(clients[i].username);
    }
    return ret;
}
