package appocalypse.appropriatelymoist;

import android.view.View;

/**
 * Created by Shelly on 2017-10-27.
 */

public class Room {
    private String roomName;
    private String hostName;
    private String roomId;
    private String clickable;
    private View.OnClickListener joinBtnPressed;

    public Room(String rN, String hN, String id, String clk, View.OnClickListener el) {
        roomName = rN;
        hostName = hN;
        roomId = id;
        clickable = clk; //"y" if yes, "n" if no
        joinBtnPressed = el;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getHostName() {
        return hostName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getClickable() { return clickable; }

    public View.OnClickListener setJoinBtnListner() {
        return joinBtnPressed;
    }

}
