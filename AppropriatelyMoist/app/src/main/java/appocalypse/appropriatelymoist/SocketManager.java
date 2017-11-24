package appocalypse.appropriatelymoist;

import android.util.Log;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Shelly on 2017-11-14.
 */

public class SocketManager {
    private static SocketManager manageSocket = new SocketManager();

    private static String url = "http://99.249.40.162:2406";
    private Socket mSocket;

    private SocketManager() {
        mSocket = null;
    }

    public synchronized static SocketManager getManageSocket() {
        return manageSocket;
    }

    public boolean connectSocket() {
        if (mSocket != null) {
            return false;
        }

        try {
            mSocket = IO.socket(url);
        } catch (URISyntaxException e) {
            Log.e("login", e.toString());
            return false;
        }

        mSocket.on(Socket.EVENT_CONNECT, onConnect);
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect);
        mSocket.connect();

        return true;
    }

    public boolean joinRoomRequest(String roomId) {
        if (mSocket == null) return false;

        mSocket.emit("join", roomId);

        return true;
    }

    public boolean loginRequest(String userName, double x, double y) {
        if (mSocket == null) return false;

        mSocket.emit("login", userName);
        mSocket.emit("lat", x);
        mSocket.emit("long", y);

        return true;
    }

    public boolean sendMessage(String mess) {
        if (mSocket == null) return false;

        mSocket.emit("message", mess);

        return true;
    }

    public boolean introRoomRequest() {
        if (mSocket == null) return false;

        mSocket.emit("intro");

        return true;
    }

    public boolean roomListRequest() {
        if (mSocket == null) return false;

        mSocket.emit("roomList");

        return true;
    }

    public boolean hostRoomRequest(String roomName) {
        if (mSocket == null) return false;

        mSocket.emit("host", roomName);
        return true;
    }

    public boolean disconnectSocket() {
        if (mSocket == null) return false;

        mSocket.close();
        mSocket = null;

        return true;
    }

    public boolean setEmitListener(String key, Emitter.Listener mListener) {
        if (mSocket == null) return false;

        mSocket.on(key, mListener);

        return true;
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            if (mSocket != null) {
                mSocket.close();
            }
            mSocket = null;
        }
    };

}
