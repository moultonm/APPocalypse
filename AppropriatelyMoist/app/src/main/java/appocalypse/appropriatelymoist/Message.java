package appocalypse.appropriatelymoist;

import java.util.ArrayList;

/**
 * Created by Shelly on 2017-10-27.
 */

public class Message {
    private String message;

    public Message(String mess) {
        message = mess;
    }

    public String getMessage() {
        return message;
    }

    private static int lastContactId = 0;

    public static ArrayList<Message> createMessagesList(int numContacts) {
        ArrayList<Message> contacts = new ArrayList<Message>();

        for (int i = 1; i <= numContacts; i++) {
            contacts.add(new Message("This is :" + i));
        }
        return contacts;
    }
}
