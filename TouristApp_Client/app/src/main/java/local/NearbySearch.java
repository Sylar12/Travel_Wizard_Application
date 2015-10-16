package local;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.example.vincent.user_interface_tourist.Hotel;
import com.example.vincent.user_interface_tourist.R;
import com.example.vincent.user_interface_tourist.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import adapters.NearbyAdapter;

/**
 * Created by vincent on 15/4/25.
 */
public class NearbySearch extends AsyncTask<Void, Void, List<NearbyPlace>> {
    private static final String API_KEY = "AIzaSyCOO2M2FxLhcL8wd7ASd64nK3S_PT-gsec";    // Browser
    private static final String TAG = NearbySearch.class.getSimpleName();

    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/search/json?";
    private double latitude;
    private double longitude;
    private double radius;
    private String types;
    private Activity act;

    public NearbySearch(double latitude, double longitude, double radius, String types, Activity act) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
        this.types = types;
        this.act = act;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<NearbyPlace> doInBackground(Void... params) {
        StringBuilder urlString = new StringBuilder(
                PLACES_SEARCH_URL);

        urlString.append("&location=");
        urlString.append(Double.toString(latitude));
        urlString.append(",");
        urlString.append(Double.toString(longitude));
        urlString.append("&radius=" + radius);
        urlString.append("&types=" + types);
        urlString.append("&sensor=false&key=" + API_KEY);

        Log.d(TAG, urlString.toString());

        try {
            String json = getJSON(urlString.toString());
            //Log.d(TAG, json);
            JSONObject object = new JSONObject(json);
            JSONArray array = object.getJSONArray("results");
            List<NearbyPlace> arrayList = new ArrayList<NearbyPlace>();
            for (int i = 0; i < array.length(); i++) {
                try {
                    NearbyPlace nearbyPlace = NearbyPlace
                            .getObjectFromJson((JSONObject) array.get(i));
                    //System.out.println(nearbyPlace);
                    arrayList.add(nearbyPlace);
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }
            }
            return arrayList;
        } catch (JSONException ex) {
            Logger.getLogger(NearbySearch.class.getName()).log(Level.SEVERE,
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
    protected void onPostExecute(List<NearbyPlace> result) {
        super.onPostExecute(result);
        if(types.equals("restaurant")) {
            ListView restList = (ListView) act.findViewById(R.id.restList);
            restList.setAdapter(new NearbyAdapter(result, act, Restaurant.class));
        }
        else {
            ListView hotelList = (ListView) act.findViewById(R.id.hotelList);
            hotelList.setAdapter(new NearbyAdapter(result, act, Hotel.class));
        }

    }
}
