package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class HostActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
    }

    //click handler for the 'Host' button
    public void openChatRoom(View view) {
        String roomName = ((EditText) findViewById(R.id.roomName)).getText().toString();
        Intent startNewActivity = new Intent(this, MessageRoomActivity.class);
        SocketManager.getManageSocket().hostRoomRequest(roomName);
        startActivity(startNewActivity);
    }

    @Override
    public void onBackPressed() {
        Intent startNewActivity = new Intent(this, JoinHostActivity.class);
        startActivity(startNewActivity);
    }

}
