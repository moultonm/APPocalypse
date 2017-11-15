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


    public void openChatRoom(View view) {

        String roomName = ((EditText) findViewById(R.id.roomName)).getText().toString();
        Intent startNewActivity = new Intent(this, MessageRoomActivity.class);
        startNewActivity.putExtra("roomName", roomName);
        startActivity(startNewActivity);
    }




}
