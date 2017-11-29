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
	private double distance;
    private View.OnClickListener joinBtnPressed;

	
    public Room(String rN, String hN, String id, String clk, View.OnClickListener el) {
        roomName = rN;
        hostName = hN;
        roomId = id;
        distance = -1;
        clickable = clk; //"y" if yes, "n" if no
        joinBtnPressed = el;
    }

    public Room(String rN, String hN, String id, String clk, String dist, View.OnClickListener el) {
        roomName = rN;
        hostName = hN;
        roomId = id;
        distance = Double.parseDouble(dist);
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
    public double getDistance() {return distance;}
	
    public View.OnClickListener setJoinBtnListner() {
        return joinBtnPressed;
    }

}
