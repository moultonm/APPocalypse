package appocalypse.appropriatelymoist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void login(View view){
        String userName = ((EditText) findViewById(R.id.nameField)).getText().toString();
        //System.out.println("user name : " + userName);
        if (userName.isEmpty()) {
            Toast toast = Toast.makeText(this, "Name cannot be empty..", Toast.LENGTH_SHORT);
            toast.show();
        } else {

            SocketManager.manageSocket.connectSocket();
            SocketManager.manageSocket.loginRequest(userName);

            UserInfo.getUserInfo().setUserInfo(userName);
            Intent startNewActivity = new Intent(this, JoinHostActivity.class);
            startActivity(startNewActivity);
        }

    }


}
