package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.City;
import model.Scene;

/**
 * Created by CMH on 4/12/15.
 */
/*loadScene function is used when the user selects a city. The server will send all the scenes in this city to app*/
public class SceneLoader extends Loader<City, Void, Void> {
    //private Activity act;
    private Client client;

    //public ProgressDialog pdLoading;

    public SceneLoader(ObjectOutputStream sendout, ObjectInputStream readin) {
        //this.act = act;
        //this.pdLoading = new ProgressDialog(act);
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
    protected Void doInBackground(City... params) {
        try {
            sendout.writeObject(32);
            sendout.writeObject(params[0]);
            //Thread.sleep(1000*1000);
        } catch (IOException e) {
            System.err.println("error when 32");
        }

        String status = null;
        ArrayList<Scene> arraylist = null;
        try {
            arraylist = (ArrayList<Scene>)readin.readObject();
            status = (String)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("readin Error");
        } catch (IOException e) {
            System.err.println("readin Error");
        }

        System.out.println("the status the server gives : " + status);
        if (arraylist != null) {
            for (int i = 0; i < arraylist.size(); i++) {
                System.out.println(arraylist.get(i).toString());
            }
        }

        client.setScenes(arraylist);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }
}
