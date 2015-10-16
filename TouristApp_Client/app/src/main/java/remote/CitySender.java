package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.City;
import model.User;

/**
 * Created by CMH on 4/12/15.
 */
/*sendCity function is used by the administrator to add a new city*/
public class CitySender extends Loader<City, Void, Void> {
    //private Activity act;

    //public ProgressDialog pdLoading;

    public CitySender(ObjectOutputStream sendout, ObjectInputStream readin) {
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
    protected Void doInBackground(City... params) {
        try {
            sendout.writeObject(21);
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


