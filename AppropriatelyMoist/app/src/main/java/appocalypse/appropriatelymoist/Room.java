package appocalypse.appropriatelymoist;

import android.view.View;

/**
 * Created by Shelly on 2017-10-27.
 */

public class Room {
    private String roomName;
    private String hostName;
    private String roomId;
    private View.OnClickListener joinBtnPressed;

    public Room(String rN, String hN, String id, View.OnClickListener el) {
        roomName = rN;
        hostName = hN;
        roomId = id;
        joinBtnPressed = el;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getHostName() {return hostName;}

    public String getRoomId() {return roomId;}

    public View.OnClickListener setJoinBtnListner(){return joinBtnPressed;}





}
