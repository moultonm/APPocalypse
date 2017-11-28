package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.net.Socket;

public class JoinHostActivity extends AppCompatActivity {
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameField = (TextView) findViewById(R.id.message);
        nameField.setText(UserInfo.getUserInfo().getUserName());
    }

    //click handler for the 'join' button
    public void join(View view) {

        Intent startNewActivity = new Intent(this, JoinActivity.class);
        startActivity(startNewActivity);
    }

    //click handler for the 'host' button
    public void host(View view) {
        Intent startNewActivity = new Intent(this, HostActivity.class);
        startActivity(startNewActivity);
    }

    @Override
    public void onBackPressed() {
        Intent startNewActivity = new Intent(this, LoginActivity.class);
        startActivity(startNewActivity);
    }

}


