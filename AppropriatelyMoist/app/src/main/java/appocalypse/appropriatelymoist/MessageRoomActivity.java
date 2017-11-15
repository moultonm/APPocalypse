package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.net.URISyntaxException;
import java.util.ArrayList;


import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MessageRoomActivity extends AppCompatActivity {

    private RecyclerView reView;
    ArrayList<Message> messages;
    Socket mSocket;
    String userName;
    String room;
    MessagesAdapter mAdapter;

    private MessagingManager mMessageRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_room);

        reView = (RecyclerView) findViewById(R.id.roomList);

        messages = new ArrayList<Message>();
        mAdapter = new MessagesAdapter(this, messages);
        reView.setAdapter(mAdapter);
        reView.setLayoutManager(new LinearLayoutManager(this));


        mMessageRoom = new MessagingManager(reView, this);



        userName = UserInfo.getUserInfo().getUserName();
        room = getIntent().getStringExtra("roomName");



        {
            try {
                mSocket = IO.socket("http://99.249.40.162:2406");
            } catch (URISyntaxException e) {
                messages.add(new Message(e.toString()));
            }
        }

        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on("message", onNewMessage);
        mSocket.connect();
        mSocket.emit("login", userName);

        if (room == null){ //we are joining a room
            room = getIntent().getStringExtra("roomId");
            mSocket.emit("join", room);

        }
        else { //we are hosting a room
            mSocket.emit("host", room);
        }

        mSocket.emit("intro");






    }

    @Override
    public void onBackPressed() {
        mSocket.close();
        mSocket = null;

        Intent startNewActivity = new Intent(this, JoinHostActivity.class);
        startNewActivity.putExtra("userName", userName);
        startActivity(startNewActivity);
    }


    public void sendMessage(View v){
        EditText message = (EditText) findViewById(R.id.messageBox);

        //mMessageRoom.sendMessage(message.getText().toString());
        mSocket.emit("message", message.getText().toString());
        //mSocket.emit("newRequest", message.getText().toString());

        message.setText("");


        //mSocket.on("")

    }
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            //String mess = (String) args[0];


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String mess = (String) args[0];
                    //mMessageRoom.recieveNewMessage(mess);
                }
            });


        }
    };



}
