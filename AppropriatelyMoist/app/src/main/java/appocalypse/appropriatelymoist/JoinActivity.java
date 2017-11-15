package appocalypse.appropriatelymoist;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.net.URISyntaxException;
import java.util.ArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class JoinActivity extends AppCompatActivity {
    private String userName;
    Socket mSocket;
    private RecyclerView reView;
    ArrayList<Room> rooms;
    RoomsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        userName = UserInfo.getUserInfo().getUserName();

        reView = (RecyclerView) findViewById(R.id.roomList);

        rooms = new ArrayList<Room>();
        mAdapter = new RoomsAdapter(this, rooms);
        reView.setAdapter(mAdapter);
        reView.setLayoutManager(new LinearLayoutManager(this));


        //ruh roh
        {
            try {
                mSocket = IO.socket("http://99.249.40.162:2406");
            } catch (URISyntaxException e) {
                Log.e("socket",e.toString());
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

                    //NEED TO BE MODIFIED
                    final String[] data = ((String)args[0]).split("/");
                    String roomId = data[0].charAt(0)+"";
                    View.OnClickListener vlistener = new View.OnClickListener() {
                        public void onClick(View v) {
                            openChatRoom(v, data[0].charAt(0)+"");
                        }
                    };

                    //MODIFY SERVER TO SEND DATA[0].substring(4)
                    Room newRoom = new Room(data[0].substring(4), data[1], roomId, vlistener);
                    rooms.add(newRoom);
                    mAdapter.notifyItemInserted(rooms.size()-1);

                    /*addRoom(data[0], data[1]).setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            openChatRoom(v, data[0].charAt(0)+"");
                        }
                    });*/
                }
            });
        }
    };

    private Button addRoom(String rname, String uname){
/*        Button myButton = new Button(this);
        myButton.setText(rname+"\nHosted by: "+uname);

        LinearLayout ll = (LinearLayout)findViewById(R.id.btnlayout);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(myButton, lp);

        //myButton.setTag("id" , "id");
        myButton.setTag("id");

        return myButton;*/
        return null;
    }
}
