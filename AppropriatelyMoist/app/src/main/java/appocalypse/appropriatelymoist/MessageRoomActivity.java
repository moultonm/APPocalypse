package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class MessageRoomActivity extends AppCompatActivity {
    ArrayList<String> messageList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Socket mSocket;
    String userName;
    String room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_room);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageList);
        //setListAdapter(adapter);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);

        userName = getIntent().getStringExtra("userName");
        room = getIntent().getStringExtra("roomName");



        {
            try {
                mSocket = IO.socket("http://99.249.40.162:2406");
            } catch (URISyntaxException e) {
                messageList.add(e.toString());
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

        mSocket.emit("message", message.getText().toString());
        mSocket.emit("newRequest", message.getText().toString());
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
//            String mess = (String) args[0];
//
//            messageList.add(mess);
//            if (messageList.size() >6) {
//                messageList.remove(0);
//            }
//
//            adapter.notifyDataSetChanged();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String mess = (String) args[0];

                    messageList.add(mess);
                    if (messageList.size() >6) {
                        messageList.remove(0);
                    }

                    adapter.notifyDataSetChanged();

                }
            });


        }
    };



}
