package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class JoinActivity extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        userName = getIntent().getStringExtra("userName");

    }

    public void openChatRoom(View view){

      Intent startNewActivity = new Intent(this, MessageRoomActivity.class);
      startNewActivity.putExtra("userName", userName);
        startNewActivity.putExtra("roomId", "0");
      startActivity(startNewActivity);

    }
}
