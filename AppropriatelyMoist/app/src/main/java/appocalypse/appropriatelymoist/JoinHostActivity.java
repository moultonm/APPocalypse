package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class JoinHostActivity extends AppCompatActivity {

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView nameField = (TextView) findViewById(R.id.message);
        userName = getIntent().getStringExtra("userName");
        nameField.setText(userName);



    }

    public void join(View view){
        Intent startNewActivity = new Intent(this, JoinActivity.class);
        startNewActivity.putExtra("userName", userName);
        startActivity(startNewActivity);
    }

    public void host(View view){
        Intent startNewActivity = new Intent(this, HostActivity.class);
        startNewActivity.putExtra("userName", userName);
        startActivity(startNewActivity);
    }
}


