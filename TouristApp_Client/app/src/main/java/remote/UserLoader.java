package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.IdPasswordCombo;
import model.User;

/**
 * Created by CMH on 4/12/15.
 */
/*loadUser function us used when the user input the id and password. The server will verify it and then send
	 * the user information to app */
public class UserLoader extends Loader<IdPasswordCombo, Void, Void> {

    private Client client;

    //public ProgressDialog pdLoading;

    public UserLoader(ObjectOutputStream sendout, ObjectInputStream readin) {
        super(readin, sendout);
        this.client = Client.getInstance();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
        //pdLoading.setMessage("\tLoading...");
        //pdLoading.show();
    }

    @Override
    protected Void doInBackground(IdPasswordCombo... params) {
        try {
            sendout.writeObject(12);
            sendout.writeObject(params[0]);
            //Thread.sleep(1000*1000);
        } catch (IOException e) {
            System.err.println("error when 12");
        }

        String status = null;
        User usr = null;
        try {
            usr = (User)readin.readObject();
            if (usr != null) {
                usr.print();
            }

            status = (String)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("readin Error");
        } catch (IOException e) {
            System.err.println("readin Error");
        }

        client.setCurrentUser(usr);
        System.out.println("the status the server gives : " + status);

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }
}
