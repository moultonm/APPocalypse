package appocalypse.appropriatelymoist;

/**
 * Created by Shelly on 2017-11-12.
 */

public class UserInfo {
    private double latitude;
    private double longitude;
    private String name;

    private static UserInfo user = new UserInfo();

    private UserInfo() {
    }

    public void setUserInfo(String userName) {
        name = userName;
    }

    public synchronized static UserInfo getUserInfo() {
        if (user == null) {
            user.setUserInfo("error");
        }
        return user;
    }

    public String getUserName() {
        return name;
    }

    public double[] getLatLong() {
        double[] temp = new double[2];
        temp[0] = latitude;
        temp[1] = longitude;

        return temp;
    }

    public void updateGPS(double x, double y) {
        //do the thing
        latitude = x;
        longitude = y;
    }

}
