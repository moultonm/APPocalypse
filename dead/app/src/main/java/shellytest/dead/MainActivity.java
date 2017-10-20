package shellytest.dead;

import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
//import com.github.nkzawa.socketio.client.IO;
//import com.github.nkzawa.socketio.client.Socket;
//import com.github.nkzawa.emitter.Emitter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.app.Application;
import android.widget.Toast;

import io.socket.client.IO;
import io.socket.client.Socket;
//import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.URISyntaxException;

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

        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.on("message", onNewMessage);
        mSocket.connect();
        mSocket.emit("intro", "NAme");


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

        adapter.notifyDataSetChanged();

        mSocket.emit("message", message.getText().toString());
        mSocket.emit("newRequest", message.getText().toString());
        message.setText("");

        //mSocket.on("")

    }
    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
//            String mess = (String) args[0];
//
//            messageList.add(mess);
//            if (messageList.size() >6) {
//                messageList.remove(0);
//            }
//
//            adapter.notifyDataSetChanged();


            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String mess = (String) args[0];

                    messageList.add(mess);
                    if (messageList.size() >6) {
                        messageList.remove(0);
                    }

                    adapter.notifyDataSetChanged();

                }
            });


        }
    };


}
