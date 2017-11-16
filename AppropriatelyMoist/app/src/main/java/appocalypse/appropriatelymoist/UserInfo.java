package appocalypse.appropriatelymoist;



/**
 * Created by Shelly on 2017-11-12.
 */

public class UserInfo {
    private int longitude;
    private int latitude;
    private String name;


    private static UserInfo user = new UserInfo();

    private UserInfo() {}

    public void setUserInfo(String userName) {
        name = userName;
        //updateGPS();
    }

    public static UserInfo getUserInfo(){
        if(user == null) {
            user.setUserInfo("error");
        }
        return user;
    }

    public String getUserName() {
        return name;
    }

    public int[] getLongLat(){
        int[] temp = new int[2];
        temp[0] = longitude;
        temp[1] = latitude;

        return temp;
    }

    public void updateGPS() {
        //do the thing
        longitude = 0;
        latitude = 0;


    }


}
