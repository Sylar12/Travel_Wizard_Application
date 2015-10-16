package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.User;

/**
 * Created by CMH on 4/12/15.
 */
/*send user information to database when sign up*/
public class UserSender extends Loader<User, Void, Void> {

    private Client client;

    //public ProgressDialog pdLoading;

    public UserSender(ObjectOutputStream sendout, ObjectInputStream readin) {
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
    protected Void doInBackground(User... params) {
        try {
            sendout.writeObject(11);
            sendout.writeObject(params[0]);
        } catch (IOException e) {
            System.err.println("error when sendUser");
        }

        int status = -1;
        try {
            status = (int)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("readin Error");
        } catch (IOException e) {
            System.err.println("readin Error");
        }

        client.setIsSignupSuccess(status);
        System.out.println("the sign up result from server : " + status);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }


}

