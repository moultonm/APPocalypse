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

    public MessageRoomManager(RecyclerView view, Context ctx){
        this.reView = view;

        messages = new ArrayList<Message>();
        mAdapter = new MessagesAdapter(ctx, messages);

        reView.setAdapter(mAdapter);
        reView.setLayoutManager(new LinearLayoutManager(ctx));

    }

    public void recieveNewMessage(String mess){
        messages.add(new Message(mess));
        mAdapter.notifyItemInserted(messages.size()-1);
        reView.scrollToPosition(mAdapter.getItemCount()-1);
    }


    public void sendMessage(String mess) {
        SocketManager.manageSocket.sendMessage(mess);
    }


}
