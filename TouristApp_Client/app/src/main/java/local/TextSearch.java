package local;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
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
/*The textsearch is invoked when the user click the details button near a scene. It search the nearby
* hotels and restaurants.*/
public class TextSearch extends AsyncTask<Void, Void, SimplePlace> {
    private static final String API_KEY = "AIzaSyCOO2M2FxLhcL8wd7ASd64nK3S_PT-gsec";    // Browser
    private static final String TAG = TextSearch.class.getSimpleName();

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    private String query;

    public TextSearch(String query) {
        this.query = query;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected SimplePlace doInBackground(Void... params) {
        StringBuilder urlString = new StringBuilder(
                PLACES_SEARCH_URL);

        urlString.append("&query=");
        urlString.append(query);
        urlString.append("&key=" +  API_KEY);
        Log.d(TAG, urlString.toString());

        try {
            String json = getJSON(urlString.toString());
            //Log.d(TAG, json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");
            SimplePlace simplePlace = SimplePlace.getObjectFromJson((JSONObject) array.get(0));

            return simplePlace;
        } catch (JSONException ex) {
            Logger.getLogger(TextSearch.class.getName()).log(Level.SEVERE,
                    null, ex);
        }
        return null;
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
    protected void onPostExecute(SimplePlace result) {
        super.onPostExecute(result);

    }
}
