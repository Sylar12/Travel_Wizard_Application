package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.City;
import model.Scene;

/**
 * Created by CMH on 4/12/15.
 */
/*this function is used by the administrator to add scene to the server database*/
public class SceneSender extends Loader<Scene, Void, Void> {

    public SceneSender(ObjectOutputStream sendout, ObjectInputStream readin) {
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
    protected Void doInBackground(Scene... params) {
        try {
            sendout.writeObject(31);
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
