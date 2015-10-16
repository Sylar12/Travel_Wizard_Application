package remote;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Scene;

/**
 * Created by CMH on 4/30/15.
 */
/*The FamousSceneSender is used to load famous scenes from the server, which is calculate by
* the number of emerging times in schedules*/
public class FamousSceneSender extends Loader<Void , Void, Void> {

    private Client client;

    public FamousSceneSender(ObjectOutputStream sendout, ObjectInputStream readin) {
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
    protected Void doInBackground(Void... params) {
        try {
            sendout.writeObject(51);

        } catch (IOException e) {
            System.err.println("error when 51");
        }

        String status = null;
        ArrayList<Scene> arraylist = null;
        try {
            arraylist = (ArrayList<Scene>)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("readin Error");
        } catch (IOException e) {
            System.err.println("readin Error");
        }

        System.out.println("result got by sending 51");
        if (arraylist != null) {
            for (int i = 0; i < arraylist.size(); i++) {
                System.out.println(arraylist.get(i).toString());
            }
        }

        client.setFamousScenes(arraylist);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }



}
