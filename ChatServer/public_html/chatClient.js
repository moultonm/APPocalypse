/*
Author - Matthew Moulton (101010631)
COMP2406, Assignment 3
*/

$(document).ready(function(){
	var userName;
	userName = prompt("What's your name?")||"User";

	var socket = io(); //connect to the server that sent this page
	socket.on('connect', function(){
		socket.emit("intro", userName);
	});

	function privateMessage(event){ //double click event handler
    if (event.shiftKey){ //if holding shift, block the clicked user
      blockUser($(this).html());
      $(this).toggleClass("blocked");
    } else { //otherwise send off a PM
      var privateObj = {};
      privateObj.username = $(this).html(); //to whoever we clicked
      privateObj.from = userName; //from ourselves (useful later)
      privateObj.message = prompt("Private message to user " + privateObj.username);
      if (privateObj.message){ //if a message was written, send it off
        socket.emit("privateMessage", privateObj);
      }
    }
	}

  function blockUser(name){ //send off a simple block request
    var data = {};
    data.username = name;
    socket.emit("blockUser", data);
  }

	$('#inputText').keypress(function(ev){ //send messages by pressing the enter key in the input box
			if(ev.which===13){
				socket.emit("message",$(this).val());
				ev.preventDefault();
				$("#chatLog").append((new Date()).toLocaleTimeString()+", "+userName+": "+$(this).val()+"\n")
				$(this).val(""); //empty the input
			}
	});

	socket.on("message",function(data){ //receive a simple chat log message
		$("#chatLog").append(data+"\n");
		$('#chatLog')[0].scrollTop=$('#chatLog')[0].scrollHeight; //scroll to the bottom
	});

  socket.on("privateMessage",function(data){ //receive a private message and maybe respond
    var privateObj = {};
    privateObj.username = data.from; //now we can simply flip the to: / from: to respond
    privateObj.from = data.username;
    privateObj.message = prompt(data.from + ": " + data.message);
    if (privateObj.message){
      socket.emit("privateMessage", privateObj);
    }
  });

	socket.on("userList", function(data){ //update the list of chatters
		$("#userList").empty();
		$("#userList").append("Users:<br><br>");
		for (var i=0; i<data.users.length; i++){
			var newItem = $("<li>"+data.users[i]+"</li>")
			newItem.dblclick(privateMessage); //add dblclick handler
			$("#userList").append(newItem);
		}
	});
});
