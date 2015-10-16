package remote;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.ScheduleUseridCombo;


/**
 * Created by CMH on 4/12/15.
 */
/*this function send schedule to the server when clicking confirm*/
public class ScheduleSender extends Loader<Void, Void, Void> {
    //private Activity act;

    private ScheduleUseridCombo combo;

    //public ProgressDialog pdLoading;

    public ScheduleSender(ObjectOutputStream sendout, ObjectInputStream readin, ScheduleUseridCombo combo) {

        super(readin, sendout);
        this.combo = combo;
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
            sendout.writeObject(41);
            System.err.println("Before Sending schedule: " + combo);
            sendout.writeObject(combo);
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
    protected void onPostExecute(java.lang.Void result) {
        super.onPostExecute(result);

        //this method will be running on UI thread

        //pdLoading.dismiss();
    }
}
