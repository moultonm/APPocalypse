package appocalypse.appropriatelymoist;

import java.util.ArrayList;

/**
 * Created by Shelly on 2017-10-27.
 */

public class Room {
    private String roomName;
    private String hostName;
    private String roomId;

    public Room(String rN, String hN, String id) {
        roomName = rN;
        hostName = hN;
        roomId = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getHostName() {return hostName;}

    public String getRoomId() {return roomId;}



}
