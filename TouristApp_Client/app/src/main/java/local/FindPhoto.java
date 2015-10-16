package local;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by vincent on 15/4/29.
 */
/*The class is used to get photo from the url of the photo*/
public class FindPhoto extends AsyncTask<Void, Void, Drawable> {
    private static final String API_KEY = "AIzaSyCOO2M2FxLhcL8wd7ASd64nK3S_PT-gsec";    // Browser
    private static final String TAG = FindPhoto.class.getSimpleName();
    private static final String PLACES_SEARCH_URL = "https://maps.googleapis.com/maps/api/place/photo?";

    private Photo photo;

    public FindPhoto(Photo photo) {
        this.photo = photo;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected Drawable doInBackground(Void... params) {
        StringBuilder urlString = new StringBuilder(
                PLACES_SEARCH_URL);

        urlString.append("&maxwidth=" + photo.getWidth());
        urlString.append("&photoreference=" + photo.getPhoto_reference());
        urlString.append("&sensor=false&key=" + API_KEY);

        Log.d(TAG, urlString.toString());

        return getImage(urlString.toString());
    }

    private Drawable getImage(String theUrl) {
        try {
            System.out.println("The searching url: "+theUrl);
            URL url = new URL(theUrl);
            InputStream is = (InputStream) url.getContent();
            Drawable d = Drawable.createFromStream(is, "pic");
            return d;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    protected void onPostExecute(Drawable result) {
        super.onPostExecute(result);

    }
}

