package local;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vincent on 15/4/29.
 */
public class PlaceDetail {

    private ArrayList<Photo> photos = new ArrayList<Photo>();
    private ArrayList<Review> reviews = new ArrayList<Review>();
    private String[] types;
    private String url="";
    private String website="";

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "PlaceDetail{" +
                "photos=" + photos +
                ", reviews=" + reviews +
                ", types=" + Arrays.toString(types) +
                ", url='" + url + '\'' +
                ", website='" + website + '\'' +
                '}';
    }

    static PlaceDetail getObjectFromJson(JSONObject jsonObject) {
        PlaceDetail detail = new PlaceDetail();
        try {
            JSONArray reviewArray = jsonObject.getJSONArray("reviews");
            int reviewNum = reviewArray.length();
            for(int i=0; i<reviewNum; i++) {
                try {
                    Review review = Review.getObjectFromJson((JSONObject) reviewArray.get(i));
                    detail.reviews.add(review);
                } catch (Exception e) {
                    Log.d(PlaceDetail.class.getSimpleName(), e.toString());
                }
            }

            JSONArray photoArray = jsonObject.getJSONArray("photos");
            int photoNum = photoArray.length();
            for(int i=0; i<photoNum; i++) {
                try {
                    Photo photo = Photo.getObjectFromJson((JSONObject) photoArray.get(i));
                    detail.photos.add(photo);
                } catch (Exception e) {
                    Log.d(PlaceDetail.class.getSimpleName(), e.toString());
                }
            }

            JSONArray tags = jsonObject.getJSONArray("types");
            int length = tags.length();
            if (length > 0) {
                detail.types = new String [length];
                for (int i=0; i<length; i++) {
                    detail.types[i] = tags.getString(i);
                }
            }
            try {
                detail.setUrl(jsonObject.getString("url"));
                detail.setWebsite(jsonObject.getString("website"));
            } catch (Exception e) {
                System.out.println("No url or website");
            }
            return detail;
        } catch (JSONException e) {
            Logger.getLogger(PlaceDetail.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
