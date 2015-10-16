package remote;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.City;

/**
 * Created by CMH on 4/12/15.
 */
/*loadCity function is used when user log in. The server will send all the city selections to app*/
public class CityLoader extends Loader<Void, Void, Void> {


private Client client;

public ProgressDialog pdLoading;

    public CityLoader(ObjectOutputStream sendout, ObjectInputStream readin) {
        super(readin, sendout);

        this.client = Client.getInstance();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //this method will be running on UI thread
        //pdLoading.setMessage("\tLoading...");
       // pdLoading.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            sendout.writeObject(22);

            //Thread.sleep(1000*1000);
        } catch (IOException e) {
            System.err.println("error when 22");
        }

        String status = null;
        ArrayList<City> arraylist = null;
        try {
            arraylist = (ArrayList<City>)readin.readObject();

            status = (String)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("classnotfound Error");
        } catch (IOException e) {
            System.err.println("io Error");
        }

        System.out.println("the status the server gives : " + status);
        if (arraylist != null) {
            for (int i = 0; i < arraylist.size(); i++) {
                arraylist.get(i).print();
            }
        }

        client.setCities(arraylist);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }
}
