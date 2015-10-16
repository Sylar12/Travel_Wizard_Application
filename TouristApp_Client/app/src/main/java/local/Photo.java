package local;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by vincent on 15/4/29.
 */
/*Phone model represent a photo, which is shown on the screen*/
public class Photo {
    private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/photo?";

    private int height;
    private int width;
    private String photo_reference;

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "height=" + height +
                ", width=" + width +
                ", photo_reference='" + photo_reference + '\'' +
                '}';
    }

    static Photo getObjectFromJson(JSONObject jsonObject) {
        try {
            Photo result = new Photo();

            result.setHeight(jsonObject.getInt("height"));
            result.setWidth(jsonObject.getInt("width"));
            result.setPhoto_reference(jsonObject.getString("photo_reference"));

            return result;
        } catch (JSONException ex) {
            Logger.getLogger(NearbyPlace.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}