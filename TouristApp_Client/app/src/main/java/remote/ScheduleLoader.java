package remote;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Schedule;

/**
 * Created by CMH on 4/12/15.
 */
 /*load all the schedules the user has made when the user want to view history*/
public class ScheduleLoader extends Loader<String, Void, Void> {

    private Client client;

    //public ProgressDialog pdLoading;

    public ScheduleLoader(ObjectOutputStream sendout, ObjectInputStream readin) {

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
    protected Void doInBackground(String... params) {
        try {
            sendout.writeObject(42);
            sendout.writeObject(params[0]);
        } catch (IOException e) {
            System.err.println("error when sendUser");
        }
        ArrayList<Schedule> schedulelist = null;
        String status = null;
        try {
            schedulelist = (ArrayList<Schedule>)readin.readObject();
            if (schedulelist != null) {
                for (int i = 0; i < schedulelist.size(); i++) {
                    System.out.println(schedulelist.get(i).toString());
                }
            }
            status = (String)readin.readObject();
        } catch (ClassNotFoundException e) {
            System.err.println("readin Error");
        } catch (IOException e) {
            System.err.println("readin Error");
        }
        System.out.println("the status the server gives : " + status);
        client.setSchedulelist(schedulelist);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }
}
