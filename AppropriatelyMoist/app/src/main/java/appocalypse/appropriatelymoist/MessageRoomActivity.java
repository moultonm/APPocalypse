package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import io.socket.emitter.Emitter;

public class MessageRoomActivity extends AppCompatActivity {
    private RecyclerView reView;

    private MessagingManager mMessageRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_message_room);
        reView = (RecyclerView) findViewById(R.id.roomList);

        mMessageRoom = new MessagingManager(reView, this);

        SocketManager.getManageSocket().setEmitListener("message", onNewMessage);
        SocketManager.getManageSocket().introRoomRequest();
    }

    @Override
    public void onBackPressed() {
        double[] latlong = UserInfo.getUserInfo().getLatLong();
        SocketManager.getManageSocket().disconnectSocket();
        SocketManager.getManageSocket().connectSocket(); //this disconn/reconn seems to cause issues sometimes
        SocketManager.getManageSocket().loginRequest(UserInfo.getUserInfo().getUserName(), latlong[0], latlong[1]);
        Intent startNewActivity = new Intent(this, JoinHostActivity.class);
        startActivity(startNewActivity);
    }

    public void sendMessage(View v) {
        EditText message = (EditText) findViewById(R.id.messageBox);
        mMessageRoom.sendMessage(message.getText().toString());
        message.setText("");
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String mess = (String) args[0];
                    mMessageRoom.recieveNewMessage(mess);
                }
            });
        }
    };

}
