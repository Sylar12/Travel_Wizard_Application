package local;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vincent on 15/4/29.
 */
/*DetailSearch is used to find the comments and the picture information of a place, such as a hotel
* or a restaurant.*/
public class DetailSearch extends AsyncTask<Void, Void, PlaceDetail> {
    private static final String API_KEY = "AIzaSyCOO2M2FxLhcL8wd7ASd64nK3S_PT-gsec";    // Browser
    private static final String TAG = DetailSearch.class.getSimpleName();

    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    private String ref;

    public DetailSearch(String ref) {
        this.ref = ref;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected PlaceDetail doInBackground(Void... params) {
        StringBuilder urlString = new StringBuilder(
                PLACES_DETAILS_URL);
        urlString.append("&reference=" + ref);
        urlString.append("&sensor=false&key=" + API_KEY);

        Log.d(TAG, urlString.toString());

        PlaceDetail detail = null;
        try {
            String json = getJSON(urlString.toString());
            JSONObject object = new JSONObject(json);
            JSONObject result=object.getJSONObject("result");
            detail = PlaceDetail.getObjectFromJson(result);
        } catch (JSONException ex) {
            Logger.getLogger(DetailSearch.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return detail;
    }

    private String getJSON(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            //System.out.println("The searching url: "+theUrl);
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(urlConnection.getInputStream()), 8);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }



    @Override
    protected void onPostExecute(PlaceDetail result) {
        super.onPostExecute(result);

    }
}

