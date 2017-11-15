package appocalypse.appropriatelymoist;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Shelly on 2017-11-14.
 */

public class SocketManager {

    public static SocketManager manageSocket = new SocketManager();
    private Socket mSocket;

    private SocketManager(){
        connectSocket();
    }

    private boolean connectSocket(){

            try {
                mSocket = IO.socket("http://99.249.40.162:2406");
            } catch (URISyntaxException e) {
                return false;
            }

        mSocket.on(Socket.EVENT_CONNECT,onConnect);
        mSocket.connect();

        return true;
    }

    public boolean sendLoginRequest(){
        if (mSocket == null) return false;

        mSocket.emit("login", UserInfo.getUserInfo().getUserName());

        return true;
    }

    public boolean joinRoomRequest(String roomId){
        if (mSocket == null) return false;

        mSocket.emit("join", roomId);

        return true;
    }

    public boolean sendMessage(String mess) {
        if (mSocket == null) return false;

        mSocket.emit("message", mess);

        return true;
    }

    public boolean hostRoomRequest(String roomName) {
        if (mSocket == null) return false;

        mSocket.emit("host", roomName);

        return true;
    }

    public void disconnectSocket(){
        mSocket.close();
        mSocket = null;

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

}
