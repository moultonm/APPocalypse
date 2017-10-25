package approp.appoco;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class joinOrHost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_or_host);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void join(View view){
        Intent startNewActivity = new Intent(this, join.class);
        startActivity(startNewActivity);
    }

    public void host(View view){
        Intent startNewActivity = new Intent(this, host.class);
        startActivity(startNewActivity);
    }
}
