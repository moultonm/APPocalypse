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
    private RecyclerView reView;
    ArrayList<Room> rooms;
    RoomsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);


        reView = (RecyclerView) findViewById(R.id.roomList);

        rooms = new ArrayList<Room>();
        mAdapter = new RoomsAdapter(this, rooms);
        reView.setAdapter(mAdapter);
        reView.setLayoutManager(new LinearLayoutManager(this));

        SocketManager.getManageSocket().setEmitListener("room", onNewRoom);
        SocketManager.getManageSocket().roomListRequest();

    }

    @Override
    public void onBackPressed() {

        Intent startNewActivity = new Intent(this, JoinHostActivity.class);
        startActivity(startNewActivity);
    }


    public void openChatRoom(View view, String id){
        SocketManager.getManageSocket().joinRoomRequest(id);
        Intent startNewActivity = new Intent(this, MessageRoomActivity.class);
        startActivity(startNewActivity);

    }


    private Emitter.Listener onNewRoom = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    //NEED TO BE MODIFIED
                    final String[] data = ((String)args[0]).split("/");

                    View.OnClickListener vlistener = new View.OnClickListener() {
                        public void onClick(View v) {
                            openChatRoom(v, data[0]);
                        }
                    };


                    Room newRoom = new Room(data[2], data[1], data[0], vlistener);
                    rooms.add(newRoom);
                    mAdapter.notifyItemInserted(rooms.size()-1);

                }
            });
        }
    };


}
