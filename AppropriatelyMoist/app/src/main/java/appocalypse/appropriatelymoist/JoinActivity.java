package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

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

        //register event handler to receive room objects from the server
        SocketManager.getManageSocket().setEmitListener("room", onNewRoom);
        SocketManager.getManageSocket().roomListRequest();
    }

    @Override
    public void onBackPressed() {
        Intent startNewActivity = new Intent(this, JoinHostActivity.class);
        startActivity(startNewActivity);
    }

    //click handler for the individual room buttons
    public void openChatRoom(View view, String id) {
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

                    final String[] data = ((String) args[0]).split("/");

                    View.OnClickListener vlistener = new View.OnClickListener() {
                        public void onClick(View v) {
                            openChatRoom(v, data[0]);
                        }
                    };



                    System.out.println("data: "+((String) args[0]));
                    Room newRoom = new Room(data[1], data[2], data[0], data[3], data[4] ,vlistener);
                    rooms.add(newRoom);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };

}
