package appocalypse.appropriatelymoist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import io.socket.client.Socket;

/**
 * Created by Shelly on 2017-11-14.
 */

public class MessageRoomManager {
    private RecyclerView reView;
    ArrayList<Message> messages;
    MessagesAdapter mAdapter;

    public MessageRoomManager(RecyclerView view, MessagesAdapter adapter, Context ctx){
        this.reView = view;
        this.mAdapter = adapter;
        messages = new ArrayList<Message>();
        reView.setAdapter(mAdapter);
        reView.setLayoutManager(new LinearLayoutManager(ctx));

    }


}
