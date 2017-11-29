package appocalypse.appropriatelymoist;

import android.content.Context;
import android.graphics.Color;
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

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        public TextView hostName;
        public TextView roomName;
        public Button roomButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            hostName = (TextView) itemView.findViewById(R.id.host_name);
            roomName = (TextView) itemView.findViewById(R.id.room_name);
            roomButton = (Button) itemView.findViewById(R.id.join_button);
        }
    }

    private List<Room> mRooms;
    // Store the context for easy access
    private Context mContext;

    // Pass in the contact array into the constructor
    public RoomsAdapter(Context context, List<Room> rooms) {
        mRooms = rooms;
        mContext = context;
    }

    // Easy access to the context object in the recyclerview
    private Context getContext() {
        return mContext;
    }

    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View roomView = inflater.inflate(R.layout.room_content, parent, false);

        // Return a new holder instance
        RoomsAdapter.ViewHolder viewHolder = new RoomsAdapter.ViewHolder(roomView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(RoomsAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Room room = mRooms.get(position);

        TextView roomName = viewHolder.roomName;
        roomName.setText("  " + room.getRoomName());

        TextView hostName = viewHolder.hostName;
        hostName.setText("Host: " + room.getHostName());

        Button joinBtn = viewHolder.roomButton;
        joinBtn.setTag(room.getRoomId());
        joinBtn.setOnClickListener(room.setJoinBtnListner());
        System.out.println("room.getClickable: " + room.getClickable());
        if (room.getClickable().equals("n")) { //disable this room if it's too far
            joinBtn.setClickable(false);
            joinBtn.setBackgroundColor(Color.parseColor("#f45f42"));

        }else if(room.getDistance() > 0.0002 && room.getDistance()< 0.001){
            joinBtn.setBackgroundColor(Color.parseColor("#f3c267"));
            joinBtn.setClickable(false);
        } else{
            joinBtn.setBackgroundColor(Color.parseColor("#67f3df"));
        }


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mRooms.size();
    }

}
