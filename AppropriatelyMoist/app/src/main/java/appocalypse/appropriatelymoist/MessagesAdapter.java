package appocalypse.appropriatelymoist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Shelly on 2017-10-27.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView messageTextView;
        //public Button roomButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            messageTextView = (TextView) itemView.findViewById(R.id.contact_name);
            //roomButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }

    private List<Message> mMessages;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public MessagesAdapter(Context context, List<Message> messages) {
        mMessages = messages;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }


    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View messageView = inflater.inflate(R.layout.chat_content, parent, false);

        // Return a new holder instance
        MessagesAdapter.ViewHolder viewHolder = new MessagesAdapter.ViewHolder(messageView);
        return viewHolder;

    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MessagesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Message message = mMessages.get(position);

        // Set item views based on your views and data model
        TextView textView = viewHolder.messageTextView;
        textView.setText(message.getMessage());
        //Button button = viewHolder.roomButton;
        //button.setText("Join");
        //button.setEnabled(contact.isOnline());
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mMessages.size();
    }

}