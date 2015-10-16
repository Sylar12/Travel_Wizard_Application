package remote;

import android.os.AsyncTask;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by CMH on 5/2/15.
 */
public abstract class Loader<T1, T2, T3> extends AsyncTask<T1, T2, T3> {
    protected ObjectOutputStream sendout;
    protected ObjectInputStream readin;

    public Loader(ObjectInputStream in, ObjectOutputStream out) {
        super();
        this.sendout = out;
        this.readin = in;
    }

    @Override
    protected T3 doInBackground(T1... params) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(T3 t3) {
        super.onPostExecute(t3);
    }
}
