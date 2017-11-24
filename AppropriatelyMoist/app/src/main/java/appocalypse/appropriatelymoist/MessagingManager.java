package appocalypse.appropriatelymoist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by Shelly on 2017-11-14.
 */

public class MessagingManager {
    private RecyclerView reView;
    ArrayList<Message> messages;
    MessagesAdapter mAdapter;

    public MessagingManager(RecyclerView view, Context ctx) {
        this.reView = view;

        messages = new ArrayList<Message>();
        mAdapter = new MessagesAdapter(ctx, messages);

        reView.setAdapter(mAdapter);
        reView.setLayoutManager(new LinearLayoutManager(ctx));
    }

    public void recieveNewMessage(String mess) {
        messages.add(new Message(mess));
        mAdapter.notifyItemInserted(messages.size() - 1);
        reView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public void sendMessage(String mess) {
        SocketManager.getManageSocket().sendMessage(mess);
    }

}
