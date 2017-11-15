package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        EditText name = (EditText) findViewById(R.id.nameField);
        setSupportActionBar(toolbar);
    }

    public void login(View view){
        String userName = ((EditText) findViewById(R.id.nameField)).getText().toString();

        UserInfo.getUserInfo().setUserInfo(userName);
        Intent startNewActivity = new Intent(this, JoinHostActivity.class);

        startActivity(startNewActivity);
    }


}
