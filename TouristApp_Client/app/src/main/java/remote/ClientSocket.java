package remote;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import model.City;
import model.User;

/**
 * Created by CMH on 4/12/15.
 */

/*The ClientSocket class is used to build a connection to server, which is invoked in Client class*/
public class ClientSocket extends AsyncTask<Void, Void, Socket> {

        private Socket clientsocket;

        private InetAddress host;
        private int port;

        private ObjectOutputStream sendout;
        private ObjectInputStream readin;

        private User user1;


        public ClientSocket(String ip, int port) throws IOException {
            this.setSocket(ip, port);
            this.connect();
            sendout = new ObjectOutputStream(clientsocket.getOutputStream());
            readin = new ObjectInputStream(clientsocket.getInputStream());
        }

        private void setSocket(String ip, int port) {
            try {
                this.host = InetAddress.getByName("10.0.23.160");
            } catch (UnknownHostException e) {
                System.err.println("fail to parse the host");
            }
            this.port = port;
        }

        private void connect() {
            try {
                clientsocket = new Socket(host, port);

            } catch (IOException e) {
                System.err.println("fail to build a client socket");
            }
            System.out.println("Build a client");

        }

    @Override
    protected Socket doInBackground(Void... params) {
        return null;
    }
}
