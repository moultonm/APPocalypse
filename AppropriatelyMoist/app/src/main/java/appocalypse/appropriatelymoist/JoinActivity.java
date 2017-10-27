package appocalypse.appropriatelymoist;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class JoinActivity extends AppCompatActivity {
    private String userName;
    Socket mSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        userName = getIntent().getStringExtra("userName");

        //ruh roh
        {
            try {
                mSocket = IO.socket("http://99.249.40.162:2406");
            } catch (URISyntaxException e) {

            }
        }

        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on("room", onNewRoom);
        mSocket.connect();
        mSocket.emit("login", userName);
        mSocket.emit("roomList");
    }

    public void openChatRoom(View view, String id){
        mSocket.close();
        Intent startNewActivity = new Intent(this, MessageRoomActivity.class);
        startNewActivity.putExtra("userName", userName);
        startNewActivity.putExtra("roomId", id);
        startActivity(startNewActivity);

    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

        }
    };

    private Emitter.Listener onNewRoom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final String[] data = ((String)args[0]).split("/");
                    addRoom(data[0], data[1]).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            openChatRoom(v, data[0].charAt(0)+"");
                        }
                    });
                }
            });
        }
    };

    private Button addRoom(String rname, String uname){
        Button myButton = new Button(this);
        myButton.setText(rname+"\nHosted by: "+uname);

        LinearLayout ll = (LinearLayout)findViewById(R.id.btnlayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);
        return myButton;
    }
}
