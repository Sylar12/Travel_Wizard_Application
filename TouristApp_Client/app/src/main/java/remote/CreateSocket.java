package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import model.User;

/**
 * Created by vincent on 15/4/11.
 */
public class CreateSocket extends AsyncTask<Void, Void, Socket>
{
    private InetAddress host;
    private int port;
    private Client cli;

    public CreateSocket(int port, Client cli) {
        try {
            // Set strict mode to get localhost address
            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);
            // Get localhost address
            //System.out.println("String of localhost: "+InetAddress.getLocalHost().getHostAddress());

            //this.host = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
            this.port = port;
            this.host = InetAddress.getByName("10.0.22.99");
            this.cli = cli;
            System.out.println("localhost name: "+this.host);
            System.out.println("port: "+this.port);


        } catch (IOException e) {
            System.out.println("Error creating client!");
            e.printStackTrace();
        }
        this.port = port;
        System.out.println("CreateSocket instance is built");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread

    }
    @Override
    protected Socket doInBackground(Void... params) {

        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

        Socket clientsocket = null;
        try {
            clientsocket = new Socket(host, port);
            ObjectOutputStream out = new ObjectOutputStream(clientsocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientsocket.getInputStream());
            cli.setSendout(out);
            cli.setReadin(in);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("error when creating socket");
        }
        return clientsocket;
    }

    @Override
    protected void onPostExecute(Socket result) {
        super.onPostExecute(result);

        //this method will be running on UI thread


    }

}


