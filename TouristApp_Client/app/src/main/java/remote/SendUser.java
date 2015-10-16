package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.User;

/**
 * Created by vincent on 15/4/11.
 */
public class SendUser extends Loader<User, Void, Void>
{
    private Activity act;

    //public ProgressDialog pdLoading;

    public SendUser(ObjectOutputStream sendout, ObjectInputStream readin) {
        super(readin, sendout);
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

        //this method will be running on background thread so don't update UI frome here
        //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here

        try {
            sendout.writeObject(11);
            sendout.writeObject(params[0]);
        } catch (IOException e) {
            System.err.println("error when sendUser");
        }

        String status = null;
        try {
            status = (String)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("readin Error");
        } catch (IOException e) {
            System.err.println("readin Error");
        }

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

