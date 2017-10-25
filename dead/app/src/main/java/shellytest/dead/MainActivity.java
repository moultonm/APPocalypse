package shellytest.dead;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    Button chatroomBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        chatroomBtn = (Button) findViewById(R.id.chatBtn);
        chatroomBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("I am done");
                EditText name = (EditText) findViewById(R.id.userName);

                goMessageRoomActivity(name.getText().toString());


            }
        });


    }


    private void goMessageRoomActivity(String userName) {

        Intent intent = new Intent(this, MessageRoomActivity.class);
        intent.putExtra("userName", userName);
        startActivity(intent);
    }



}
