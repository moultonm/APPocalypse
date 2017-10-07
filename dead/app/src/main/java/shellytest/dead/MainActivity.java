package shellytest.dead;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> messageList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    Socket mSocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        {
            try {
                mSocket = IO.socket("http://99.249.40.162:2406");
            } catch (URISyntaxException e) {
                messageList.add(e.toString());
            }
        }
        mSocket.connect();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messageList);
        //setListAdapter(adapter);
        ListView listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
    }


    public void sendMessage(View v){
        EditText message = (EditText) findViewById(R.id.messageBox);
        System.out.println("HELO");
        messageList.add(message.getText().toString());
        if (messageList.size() >6) {
            messageList.remove(0);
        }
        message.setText("");
        adapter.notifyDataSetChanged();
        mSocket.emit("intro", "NAme");
        mSocket.emit("newRequest", message.getText().toString());


    }


}
